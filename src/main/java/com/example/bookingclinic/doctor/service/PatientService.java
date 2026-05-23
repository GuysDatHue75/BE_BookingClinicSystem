package com.example.bookingclinic.doctor.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.doctor.dto.Patient.PatientRequestDTO;
import com.example.bookingclinic.doctor.dto.Patient.PatientResponseDTO;
import com.example.bookingclinic.doctor.dto.Patient.PatientmanagerDTO;
import com.example.bookingclinic.doctor.entity.Patient;
import com.example.bookingclinic.doctor.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    // 1. Lấy danh sách bệnh nhân
    public Page<PatientmanagerDTO> getDetailedPatients(String maBacSi, String maPhongKham, String keyword, int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size);

        String searchKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;

        // Khai báo trạng thái cần lọc
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

        // MAPPING từ Patient (bảng benh_nhan nay đã có đủ các trường này)
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

        // Lấy thông tin Họ Tên từ Account liên kết
        if (patient.getTaiKhoan() != null) {
            response.setHoVaTen(patient.getTaiKhoan().getHoVaTen());
        }

        return response;
    }

    // 3. Chỉnh sửa thông tin bệnh nhân
    public PatientResponseDTO updatePatient(PatientRequestDTO dto) {
        Patient patient = patientRepository.findById(dto.getMaBenhNhan())
                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy hồ sơ bệnh nhân!"));

        // Cập nhật thông tin (Nếu DTO có truyền thêm SĐT, Email, Địa chỉ... sếp có thể
        // bổ sung set vào đây)
        patient.setChieuCao(dto.getChieuCao());
        patient.setCanNang(dto.getCanNang());
        patient.setTienSuBenhAn(dto.getTienSuBenhAn());
        patient.setTinhTrangSucKhoe(dto.getTinhTrangSucKhoe());
        patient.setNhomMau(dto.getNhomMau());

        // Lưu vào Database
        Patient savedPatient = patientRepository.save(patient);

        // MAPPING trả về (Logic tương tự như hàm Detail)
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