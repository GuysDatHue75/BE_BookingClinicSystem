package com.example.bookingclinic.doctor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.doctor.dto.Patient.PatientRequestDTO;
import com.example.bookingclinic.doctor.dto.Patient.PatientResponseDTO;
import com.example.bookingclinic.doctor.dto.Prescription.PrescriptionDetailDTO;
import com.example.bookingclinic.doctor.dto.Patient.PatientmanagerDTO;
import com.example.bookingclinic.doctor.entity.Patient;
import com.example.bookingclinic.doctor.entity.Prescription.MedicalRecords;
import com.example.bookingclinic.doctor.entity.Prescription.Prescription;
import com.example.bookingclinic.doctor.repository.PatientRepository;
import com.example.bookingclinic.doctor.repository.PrescriptionRepository.PrescriptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PrescriptionRepository prescriptionRepository;

    // 1. Lấy danh sách bệnh nhân duy nhất (Đã Khám)
    public Page<PatientmanagerDTO> getDetailedPatients(String maBacSi, String maPhongKham, String keyword, int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size);
        String searchKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;
        String trangThai = "DaKham";

        return patientRepository.findDetailedPatients(
                maBacSi,
                maPhongKham,
                searchKeyword,
                trangThai,
                pageable);
    }

    // 2. Lấy chi tiết 1 bệnh nhân
    public PatientResponseDTO getPatientDetail(String maBenhNhan) {
        Patient patient = patientRepository.findById(maBenhNhan)
                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy mã bệnh nhân: " + maBenhNhan));

        PatientResponseDTO response = new PatientResponseDTO();

        // MAPPING từ Patient
        response.setMaBenhNhan(patient.getMaBenhNhan());
        response.setNgaySinh(patient.getNgaySinh());
        response.setGioiTinh(patient.getGioiTinh());
        response.setSoDienThoai(patient.getSoDienThoai());
        response.setEmail(patient.getEmail());
        response.setDiaChi(patient.getDiaChi());
        response.setQueQuan(patient.getQueQuan());
        response.setNgheNghiep(patient.getNgheNghiep());
        response.setChieuCao(patient.getChieuCao());
        response.setCanNang(patient.getCanNang());
        response.setNhomMau(patient.getNhomMau());
        response.setTienSuBenhAn(patient.getTienSuBenhAn());
        response.setTinhTrangSucKhoe(patient.getTinhTrangSucKhoe());
        response.setAccount(patient.getTaiKhoan());
        // Lấy thông tin Họ Tên từ Account liên kết
        if (patient.getTaiKhoan() != null) {
            response.setHoVaTen(patient.getTaiKhoan().getHoVaTen());
        }
        List<Prescription> prescriptions = prescriptionRepository.findHistoryByMaBenhNhan(maBenhNhan);

        if (prescriptions != null && !prescriptions.isEmpty()) {
            List<PatientResponseDTO.MedicalHistoryDTO> historyList = prescriptions.stream().map(p -> {
                PatientResponseDTO.MedicalHistoryDTO history = new PatientResponseDTO.MedicalHistoryDTO();

                // Thông tin đơn thuốc
                history.setMaSoDonThuoc(p.getMaSoDonThuoc());
                history.setNgayLap(p.getNgayLap());

                // Thông tin hồ sơ khám & Bác sĩ
                if (p.getMedicalRecord() != null) {
                    history.setMaHoSo(p.getMedicalRecord().getMaHoSo());
                    history.setTrieuChung(p.getMedicalRecord().getTrieuChung());
                    history.setChuanDoan(p.getMedicalRecord().getChuanDoan());
                    history.setKetLuan(p.getMedicalRecord().getKetLuan());
                    history.setGhiChu(p.getMedicalRecord().getGhiChu());

                    // Móc tên bác sĩ qua bảng Appointment -> Doctor -> Account
                    if (p.getMedicalRecord().getAppointment() != null
                            && p.getMedicalRecord().getAppointment().getBacSi() != null
                            && p.getMedicalRecord().getAppointment().getBacSi().getTaiKhoan() != null) {
                        history.setTenBacSi(
                                p.getMedicalRecord().getAppointment().getBacSi().getTaiKhoan().getHoVaTen());
                    }
                }

                // Thông tin chi tiết các loại thuốc trong đơn
                // Thông tin chi tiết các loại thuốc trong đơn
                if (p.getChiTietDonThuoc() != null && !p.getChiTietDonThuoc().isEmpty()) {
                    List<PrescriptionDetailDTO> listThuoc = p.getChiTietDonThuoc().stream()
                            .map(chiTiet -> {
                                PrescriptionDetailDTO thuoc = new PrescriptionDetailDTO();
                                thuoc.setTenThuoc(chiTiet.getTenThuoc());
                                thuoc.setLieuDung(chiTiet.getLieuDung());
                                thuoc.setSoLuong(chiTiet.getSoLuong());
                                thuoc.setDonVi(chiTiet.getDonVi());
                                thuoc.setGhiChu(chiTiet.getGhiChu());
                                return thuoc;
                            }).collect(Collectors.toList());
                    history.setDanhSachThuoc(listThuoc);
                } else {
                    history.setDanhSachThuoc(new ArrayList<>());
                }

                return history;
            }).collect(Collectors.toList());

            response.setLichSuKham(historyList);
        } else {
            // Nếu bệnh nhân chưa khám lần nào, trả về mảng rỗng (để Frontend không bị lỗi
            // null map)
            response.setLichSuKham(new ArrayList<>());
        }

        return response;
    }

    // 3. Chỉnh sửa thông tin bệnh nhân
    public PatientResponseDTO updatePatient(PatientRequestDTO dto) {
        Patient patient = patientRepository.findById(dto.getMaBenhNhan())
                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy hồ sơ bệnh nhân!"));

        patient.setChieuCao(dto.getChieuCao());
        patient.setCanNang(dto.getCanNang());
        patient.setTienSuBenhAn(dto.getTienSuBenhAn());
        patient.setTinhTrangSucKhoe(dto.getTinhTrangSucKhoe());
        patient.setNhomMau(dto.getNhomMau());

        // Lưu vào Database
        Patient savedPatient = patientRepository.save(patient);

        // MAPPING trả về
        PatientResponseDTO response = new PatientResponseDTO();
        response.setMaBenhNhan(savedPatient.getMaBenhNhan());
        response.setNgaySinh(savedPatient.getNgaySinh());
        response.setGioiTinh(savedPatient.getGioiTinh());
        response.setSoDienThoai(savedPatient.getSoDienThoai());
        response.setEmail(savedPatient.getEmail());
        response.setDiaChi(savedPatient.getDiaChi());
        response.setQueQuan(savedPatient.getQueQuan());
        response.setNgheNghiep(savedPatient.getNgheNghiep());
        response.setChieuCao(savedPatient.getChieuCao());
        response.setCanNang(savedPatient.getCanNang());
        response.setNhomMau(savedPatient.getNhomMau());
        response.setTienSuBenhAn(savedPatient.getTienSuBenhAn());
        response.setTinhTrangSucKhoe(savedPatient.getTinhTrangSucKhoe());

        if (savedPatient.getTaiKhoan() != null) {
            System.out.println("check tai khoan: " + savedPatient.getTaiKhoan().getHoVaTen());
            response.setHoVaTen(savedPatient.getTaiKhoan().getHoVaTen());
        }

        return response;
    }

    // 4. Hàm xóa bệnh nhân
    public Patient deletePatient(String maBenhNhan) {
        Patient patient = patientRepository.findById(maBenhNhan)
                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy bệnh nhân muốn xóa!"));
        patientRepository.delete(patient);
        return patient;
    }
}