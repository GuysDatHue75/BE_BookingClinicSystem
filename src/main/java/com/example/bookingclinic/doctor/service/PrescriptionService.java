package com.example.bookingclinic.doctor.service;

import com.example.bookingclinic.doctor.repository.PrescriptionRepository.MedicalRecordsRepository;
import com.example.bookingclinic.doctor.repository.PrescriptionRepository.PrescriptionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.doctor.dto.Prescription.MedicalRecordsDTO;
import com.example.bookingclinic.doctor.dto.Prescription.PrescriptionDTO;
import com.example.bookingclinic.doctor.dto.Prescription.PrescriptionDetailDTO;
import com.example.bookingclinic.doctor.entity.ConfirmAppointment;
import com.example.bookingclinic.doctor.entity.Prescription.MedicalRecords;
import com.example.bookingclinic.doctor.entity.Prescription.Prescription;
import com.example.bookingclinic.doctor.entity.Prescription.PrescriptionDetail;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    // 1. Tạo đơn thuốc
    @Transactional
    public String CreatePrescription(MedicalRecordsDTO dto) {
        // B1. Lưu hồ sơ khám
        MedicalRecords medicalRecords = new MedicalRecords();
        medicalRecords.setMaHoSo(dto.getMaHoSo());

        // FIX 1: Tạo Object ConfirmAppointment giả để gán vào MedicalRecords thay vì
        // gán String
        ConfirmAppointment lichKham = new ConfirmAppointment();
        lichKham.setMaLichKham(dto.getMaLichKham());
        medicalRecords.setConfirmAppointment(lichKham);

        medicalRecords.setTrieuChung(dto.getTrieuChung());
        medicalRecords.setChuanDoan(dto.getChuanDoan());
        medicalRecords.setKetLuan(dto.getKetLuan());
        medicalRecords.setGhiChu(dto.getGhiChu());
        medicalRecords.setNgayLap(LocalDateTime.now());

        medicalRecordsRepository.save(medicalRecords);

        // B2 Tạo và lưu đơn thuốc
        Prescription prescription = new Prescription();
        Integer maxNumber = prescriptionRepository.getMaxMaSoDonThuoc();
        int nextNumber = (maxNumber == null) ? 1 : maxNumber + 1;
        String maDonThuoc = String.format("DT%02d", nextNumber);

        prescription.setMaSoDonThuoc(maDonThuoc);
        prescription.setMedicalRecord(medicalRecords);
        prescription.setNgayLap(LocalDateTime.now());

        // B2.2. tạo danh sách chi tiết thuốc
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

        // gán ds con vào cha
        prescription.setChiTietDonThuoc(details);
        prescriptionRepository.save(prescription);

        return "Tạo đơn thuốc thành công: " + maDonThuoc;
    }

    // 2. Xem 1 đơn thuốc
    public PrescriptionDTO getPrescriptionById(String maDonThuoc) {
        Prescription prescription = prescriptionRepository.findById(maDonThuoc)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn thuốc có mã: " + maDonThuoc));
        return mapToDTO(prescription);
    }

    // 3. xem tất cả các đơn thuốc
    public List<PrescriptionDTO> getAllPrescriptions() {
        return prescriptionRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // 4. update đơn thuốc
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
            // Add nguyên một list mới vào lại
            prescription.getChiTietDonThuoc().addAll(newDetails);
        }
        // 4. Lưu lại (Hibernate sẽ tự lo việc UPDATE hồ sơ, DELETE thuốc cũ, INSERT
        // thuốc mới)
        Prescription updatedEntity = prescriptionRepository.save(prescription);

        return mapToDTO(updatedEntity);

    }

    // 5. Delete 1 đơn thuốc
    @Transactional
    public String deletePrescription(String maDonThuoc) {
        Prescription prescription = prescriptionRepository.findById(maDonThuoc)
                .orElseThrow(() -> new RuntimeException("Không tim thấy mã đơn thuốc muốn xóa: " + maDonThuoc));

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

        if (prescription.getMedicalRecord() != null) {
            prescriptionDTO.setTrieuChung(prescription.getMedicalRecord().getTrieuChung());
            prescriptionDTO.setChuanDoan(prescription.getMedicalRecord().getChuanDoan());
            prescriptionDTO.setKetLuan(prescription.getMedicalRecord().getKetLuan());
            prescriptionDTO.setGhiChuHoSo(prescription.getMedicalRecord().getGhiChu());

            // FIX 2: Phải gọi getConfirmAppointment() vì entity của bạn đặt tên biến như
            // vậy
            if (prescription.getMedicalRecord().getConfirmAppointment() != null) {
                var lichKham = prescription.getMedicalRecord().getConfirmAppointment();

                // FIX 3: Lấy dữ liệu Bệnh Nhân từ thuộc tính "taiKhoan"
                if (lichKham.getTaiKhoan() != null) {
                    prescriptionDTO.setTenBenhNhan(lichKham.getTaiKhoan().getHoVaTen());
                    prescriptionDTO.setSdtBenhNhan(lichKham.getTaiKhoan().getSoDienThoai());
                }

                // Lấy dữ liệu Bác Sĩ, Phòng Khám từ thuộc tính "lichLamViec"
                if (lichKham.getLichLamViec() != null) {
                    prescriptionDTO.setTenBacSi(lichKham.getLichLamViec().getBacSi().getTaiKhoan().getHoVaTen());
                    prescriptionDTO.setTenPhongKham(lichKham.getLichLamViec().getBacSi().getMaPhongKham());
                }
            }
        }

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






