package com.example.bookingclinic.doctor.service.Prescription;

import com.example.bookingclinic.doctor.repository.DoctorRepository;
import com.example.bookingclinic.doctor.repository.PatientRepository;
import com.example.bookingclinic.doctor.repository.PrescriptionRepository.FilePrescriptionRepository;
import com.example.bookingclinic.doctor.repository.PrescriptionRepository.MedicalRecordsRepository;
import com.example.bookingclinic.doctor.repository.PrescriptionRepository.PrescriptionRepository;
import com.example.bookingclinic.doctor.repository.ScheduleRepository.AppointmentRepository;
import com.example.bookingclinic.user.entity.UPatient;
import com.example.bookingclinic.user.repository.UPatientRepository;
import com.google.api.client.util.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookingclinic.doctor.dto.doctor;
import com.example.bookingclinic.doctor.dto.Prescription.MedicalRecordsDTO;
import com.example.bookingclinic.doctor.dto.Prescription.PrescriptionDTO;
import com.example.bookingclinic.doctor.dto.Prescription.PrescriptionDetailDTO;
import com.example.bookingclinic.doctor.entity.Doctor;
import com.example.bookingclinic.doctor.entity.Patient;
import com.example.bookingclinic.doctor.entity.Prescription.FilePrescription;
import com.example.bookingclinic.doctor.entity.Prescription.MedicalRecords;
import com.example.bookingclinic.doctor.entity.Prescription.Prescription;
import com.example.bookingclinic.doctor.entity.Prescription.PrescriptionDetail;
import com.example.bookingclinic.doctor.entity.Schedule.Appointment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final UPatientRepository uPatientRepository;

    // 1. Tạo đơn thuốc và hoàn tất cuộc khám
    @Transactional
    public Map<String, Object> createPrescription(MedicalRecordsDTO dto) {

        // B0. Tìm lịch khám gốc và cập nhật trạng thái sang "DaKham"
        Appointment lichKham = appointmentRepository.findById(dto.getMaLichKham())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch khám với mã: " + dto.getMaLichKham()));

        lichKham.setTrangThai("DaKham");
        appointmentRepository.save(lichKham);

        // B1. Khởi tạo và lưu hồ sơ khám (MedicalRecords)
        MedicalRecords medicalRecords = new MedicalRecords();
        Doctor doctor = doctorRepository.findByMaBacSi(lichKham.getBacSi().getMaBacSi());
        UPatient uPatient = uPatientRepository.findByMaBenhNhan(lichKham.getBenhNhan().getMaBenhNhan());

        Integer maxHoSoNumber = medicalRecordsRepository.getMaxMaHoSo();
        int nextHoSoNumber = (maxHoSoNumber == null) ? 1 : maxHoSoNumber + 1;
        String maHoSoTuDong = String.format("HS%02d", nextHoSoNumber);

        medicalRecords.setMaHoSo(maHoSoTuDong);
        medicalRecords.setAppointment(lichKham);
        medicalRecords.setTrieuChung(dto.getTrieuChung());
        medicalRecords.setChuanDoan(dto.getChuanDoan());
        medicalRecords.setKetLuan(dto.getKetLuan());
        medicalRecords.setGhiChu(dto.getGhiChu());
        medicalRecords.setNgayLap(LocalDateTime.now());

        medicalRecordsRepository.save(medicalRecords);

        // B2. Tạo và lưu đơn thuốc (Prescription)
        Prescription prescription = new Prescription();
        Integer maxNumber = prescriptionRepository.getMaxMaSoDonThuoc();
        int nextNumber = (maxNumber == null) ? 1 : maxNumber + 1;
        String maDonThuoc = String.format("DT%02d", nextNumber);

        prescription.setMaSoDonThuoc(maDonThuoc);
        prescription.setMedicalRecord(medicalRecords);
        prescription.setNgayLap(LocalDateTime.now());
        prescription.setDoctor(doctor);
        prescription.setPatient(uPatient);

        // B3. Tạo danh sách chi tiết các loại thuốc theo đơn
        if (dto.getDanhSachThuoc() != null) {
            List<PrescriptionDetail> details = dto.getDanhSachThuoc().stream().map(detailDTO -> {
                PrescriptionDetail detail = new PrescriptionDetail();
                detail.setPrescription(prescription);
                detail.setTenThuoc(detailDTO.getTenThuoc());
                detail.setLieuDung(detailDTO.getLieuDung());
                detail.setSoLuong(detailDTO.getSoLuong());
                detail.setDonVi(detailDTO.getDonVi());
                detail.setGhiChu(detailDTO.getGhiChu());
                return detail;
            }).collect(Collectors.toList());

            prescription.setChiTietDonThuoc(details);
        }

        prescriptionRepository.save(prescription);

        // Trả về các mã định danh cần thiết cho Client
        Map<String, Object> response = new HashMap<>();
        response.put("maHoSo", maHoSoTuDong);
        response.put("maSoDonThuoc", maDonThuoc);

        return response;
    }

    // 2. Xem 1 đơn thuốc
    public PrescriptionDTO getPrescriptionById(String maDonThuoc) {
        Prescription prescription = prescriptionRepository.findById(maDonThuoc)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn thuốc có mã: " + maDonThuoc));
        return mapToDTO(prescription);
    }

    // 3. Xem tất cả các đơn thuốc
    public List<PrescriptionDTO> getAllPrescriptions() {
        return prescriptionRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // 4. Update đơn thuốc
    @Transactional
    public PrescriptionDTO updatePrescription(String maDonThuoc, MedicalRecordsDTO dto) {
        // 1 Tìm đơn thuốc
        Prescription prescription = prescriptionRepository.findById(maDonThuoc)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã đơn thuốc: " + maDonThuoc));

        // 2. map từ DTO -> Entity
        MedicalRecords medicalRecords = prescription.getMedicalRecord();
        if (medicalRecords != null) {
            medicalRecords.setTrieuChung(dto.getTrieuChung());
            medicalRecords.setChuanDoan(dto.getChuanDoan());
            medicalRecords.setKetLuan(dto.getKetLuan());
            medicalRecords.setGhiChu(dto.getGhiChu());
        }
        // 3. cập nhật chi tiết đơn thuốc ( thêm / sửa / xóa)
        prescription.getChiTietDonThuoc().clear();
        if (dto.getDanhSachThuoc() != null) {
            List<PrescriptionDetail> newDetails = dto.getDanhSachThuoc().stream().map(detailDTO -> {
                PrescriptionDetail detail = new PrescriptionDetail();
                detail.setPrescription(prescription);
                detail.setTenThuoc(detailDTO.getTenThuoc());
                detail.setLieuDung(detailDTO.getLieuDung());
                detail.setSoLuong(detailDTO.getSoLuong());
                detail.setDonVi(detailDTO.getDonVi());
                detail.setGhiChu(detailDTO.getGhiChu());
                return detail;
            }).collect(Collectors.toList());
            prescription.getChiTietDonThuoc().addAll(newDetails);
        }
        Prescription updatedEntity = prescriptionRepository.save(prescription);
        return mapToDTO(updatedEntity);
    }

    // 5. Delete 1 đơn thuốc
    @Transactional
    public String deletePrescription(String maDonThuoc) {
        Prescription prescription = prescriptionRepository.findById(maDonThuoc)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã đơn thuốc muốn xóa: " + maDonThuoc));

        prescriptionRepository.delete(prescription);
        return "Đã xóa thành công đơn thuốc có mã là : " + maDonThuoc;
    }

    // 6. Lọc đơn thuốc
    public Page<PrescriptionDTO> filterPrescriptionsByDate(LocalDate tuNgay, LocalDate denNgay, int page, int size) {
        LocalDateTime startOfDay = tuNgay.atStartOfDay();
        LocalDateTime endOfDay = denNgay.atTime(LocalTime.MAX);
        Pageable pageable = PageRequest.of(page, size, Sort.by("ngayLap").descending());

        Page<Prescription> prescriptions = prescriptionRepository.findByNgayLapBetween(startOfDay, endOfDay, pageable);
        return prescriptions.map(this::mapToDTO);
    }

    // ------------------------------------------------------------------------
    private PrescriptionDTO mapToDTO(Prescription prescription) {
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setMaSoDonThuoc(prescription.getMaSoDonThuoc());
        prescriptionDTO.setNgayLap(prescription.getNgayLap());

        // Ánh xạ thông tin từ Hồ sơ khám bệnh và Lịch hẹn liên quan
        MedicalRecords record = prescription.getMedicalRecord();
        if (record != null) {
            prescriptionDTO.setMaHoSo(record.getMaHoSo());
            prescriptionDTO.setTrieuChung(record.getTrieuChung());
            prescriptionDTO.setChuanDoan(record.getChuanDoan());
            prescriptionDTO.setKetLuan(record.getKetLuan());
            prescriptionDTO.setGhiChuHoSo(record.getGhiChu());

            Appointment lichKham = record.getAppointment();
            if (lichKham != null) {
                // Ánh xạ thông tin bệnh nhân
                if (lichKham.getBenhNhan() != null) {
                    if (lichKham.getBenhNhan().getTaiKhoan() != null) {
                        prescriptionDTO.setTenBenhNhan(lichKham.getBenhNhan().getTaiKhoan().getHoVaTen());
                    }
                    prescriptionDTO.setSdtBenhNhan(lichKham.getBenhNhan().getSoDienThoai());
                }

                // Ánh xạ thông tin bác sĩ điều trị
                if (lichKham.getBacSi() != null) {
                    if (lichKham.getBacSi().getTaiKhoan() != null) {
                        prescriptionDTO.setTenBacSi(lichKham.getBacSi().getTaiKhoan().getHoVaTen());
                    }
                    prescriptionDTO.setTenPhongKham(lichKham.getBacSi().getMaPhongKham());
                }
            }
        }

        // Ánh xạ danh sách thuốc có trong đơn thuốc
        if (prescription.getChiTietDonThuoc() != null) {
            List<PrescriptionDetailDTO> listThuoc = prescription.getChiTietDonThuoc().stream().map(detail -> {
                PrescriptionDetailDTO detailDTO = new PrescriptionDetailDTO();
                detailDTO.setTenThuoc(detail.getTenThuoc());
                detailDTO.setLieuDung(detail.getLieuDung());
                detailDTO.setSoLuong(detail.getSoLuong());
                detailDTO.setDonVi(detail.getDonVi());
                detailDTO.setGhiChu(detail.getGhiChu());
                return detailDTO;
            }).toList();

            prescriptionDTO.setDanhSachThuoc(listThuoc);
        }

        return prescriptionDTO;

    }
}