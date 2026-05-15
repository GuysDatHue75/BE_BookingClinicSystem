package com.example.bookingclinic.doctor.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.doctor.dto.Patient.PatientRequestDTO;
import com.example.bookingclinic.doctor.dto.Patient.PatientResponseDTO;
import com.example.bookingclinic.doctor.dto.Patient.PatientmanagerDTO;
import com.example.bookingclinic.doctor.entity.Account;
import com.example.bookingclinic.doctor.entity.Patient;
import com.example.bookingclinic.doctor.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class PatientService {
    private final PatientRepository patientRepository;

    public Page<PatientmanagerDTO> getDetailedPatients(String maBacSi, String maPhongKham, String keyword, int page,
            int size) {
        // 1. Tạo đối tượng phân trang (Spring Data JPA sẽ tự lo phần LIMIT/OFFSET)
        Pageable pageable = PageRequest.of(page, size);

        // 2. Xử lý keyword: Nếu rỗng thì để null, nếu có thì giữ nguyên (Query sẽ tự
        // bọc % %)
        String searchKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;

        // 3. Khai báo trạng thái cần lọc
        String trangThai = "Đã khám";

        // 4. Gọi Repository. Chú ý truyền đủ các tham số theo đúng thứ tự trong
        // Repository
        return patientRepository.findDetailedPatients(
                maBacSi,
                maPhongKham,
                searchKeyword,
                trangThai,
                pageable);
    }

    public PatientResponseDTO getPatientDetail(String maBenhNhan) {
        Patient patient = patientRepository.findById(maBenhNhan)
                .orElseThrow(() -> new RuntimeException("lỗi không timg thấy mã bệnh nhân :" + maBenhNhan));

        // 1. MAPPING sang ResponseDTO để trả về cho Client
        PatientResponseDTO response = new PatientResponseDTO();
        response.setMaBenhNhan(patient.getMaBenhNhan());
        response.setQueQuan(patient.getQueQuan());
        response.setNgheNghiep(patient.getNgheNghiep());
        response.setChieuCao(patient.getChieuCao());
        response.setCanNang(patient.getCanNang());
        response.setTienSuBenhAn(patient.getTienSuBenhAn());
        response.setTinhTrangSucKhoe(patient.getTinhTrangSucKhoe());
        response.setNhomMau(patient.getNhomMau());

        // Lấy thông tin từ Account liên kết (nếu có) để điền nốt vào DTO
        if (patient.getTaiKhoan() != null) {
            Account acc = patient.getTaiKhoan();
            response.setHoVaTen(acc.getHoVaTen());
            response.setSoDienThoai(acc.getSoDienThoai());
            response.setEmail(acc.getEmail());
            response.setNgaySinh(acc.getNgaySinh());
            response.setGioiTinh(acc.getGioiTinh());
            response.setDiaChi(acc.getDiaChi());
        }

        return response;
    }

    // // 2. Thêm bệnh nhân
    // public Patient addPatient(PatientRequestDTO dto) {
    // // 1. Kiểm tra Mã BN đã tồn tại trong db chưa
    // Patient patient = patientRepository.findById(dto.getMaBenhNhan())
    // .orElseThrow(() -> new RuntimeException("Lỗi: Bệnh nhân không tồn tại"));

    // Account account = new Account();
    // account.setNgaySinh(dto.getNgaySinh());
    // account.setSoDienThoai(dto.getSoDienThoai());
    // account.setGioiTinh(dto.getGioiTinh());
    // account.setEmail(dto.getEmail());
    // account.setDiaChi(dto.getDiaChi());
    // accountRepository.save(account); // Lưu lại thông tin cá nhân vào bảng
    // tai_khoan

    // // 3. Tạo hồ sơ Patient
    // patient.setQueQuan(dto.getQueQuan());
    // patient.setNgheNghiep(dto.getNgheNghiep());
    // patient.setChieuCao(dto.getChieuCao());
    // patient.setCanNang(dto.getCanNang());
    // patient.setTienSuBenhAn(dto.getTienSuBenhAn());
    // patient.setTinhTrangSucKhoe(dto.getTinhTrangSucKhoe());
    // patient.setNhomMau(dto.getNhomMau());
    // return patientRepository.save(patient);
    // }

    // 3. Chỉnh sửa thông tin bệnh nhân
    public PatientResponseDTO updatePatient(PatientRequestDTO dto) {
        // 1. Tìm hồ sơ Patient
        Patient patient = patientRepository.findById(dto.getMaBenhNhan())
                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy hồ sơ bệnh nhân!"));

        // 2. Cập nhật thông tin bệnh lý (vào Entity Patient)
        patient.setChieuCao(dto.getChieuCao());
        patient.setCanNang(dto.getCanNang());
        patient.setTienSuBenhAn(dto.getTienSuBenhAn());
        patient.setTinhTrangSucKhoe(dto.getTinhTrangSucKhoe());
        patient.setNhomMau(dto.getNhomMau());

        // 3. Lưu vào Database
        Patient savedPatient = patientRepository.save(patient);

        // 4. MAPPING sang ResponseDTO để trả về cho Client
        PatientResponseDTO response = new PatientResponseDTO();
        response.setMaBenhNhan(savedPatient.getMaBenhNhan());
        response.setQueQuan(savedPatient.getQueQuan());
        response.setNgheNghiep(savedPatient.getNgheNghiep());
        response.setChieuCao(savedPatient.getChieuCao());
        response.setCanNang(savedPatient.getCanNang());
        response.setTienSuBenhAn(savedPatient.getTienSuBenhAn());
        response.setTinhTrangSucKhoe(savedPatient.getTinhTrangSucKhoe());
        response.setNhomMau(savedPatient.getNhomMau());

        // Lấy thông tin từ Account liên kết (nếu có) để điền nốt vào DTO
        if (savedPatient.getTaiKhoan() != null) {
            Account acc = savedPatient.getTaiKhoan();
            response.setHoVaTen(acc.getHoVaTen());
            response.setSoDienThoai(acc.getSoDienThoai());
            response.setEmail(acc.getEmail());
            response.setNgaySinh(acc.getNgaySinh());
            response.setGioiTinh(acc.getGioiTinh());
            response.setDiaChi(acc.getDiaChi());
        }

        return response;
    }

    // 4. Hàm xóa bệnh nhân
    public Patient deletePatient(String maBenhNhan) {
        Patient patient = patientRepository.findById(maBenhNhan)
                .orElseThrow(() -> new RuntimeException("Lỗi : Không tìm thấy bệnh nhân muốn xóa!"));
        patientRepository.delete(patient);
        return patient;
    }
}
