package com.example.bookingclinic.doctor.service;

import org.springframework.stereotype.Service;
import com.example.bookingclinic.doctor.dto.DoctorProfileDTO;
import com.example.bookingclinic.doctor.entity.Account;
import com.example.bookingclinic.doctor.entity.Doctor;
import com.example.bookingclinic.doctor.repository.AccountRepository;
import com.example.bookingclinic.doctor.repository.DoctorRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorProfileService {
    private final DoctorRepository doctorRepository;
    private final AccountRepository accountRepository;

    // lấy hồ sơ bác sĩ
    public DoctorProfileDTO getDoctorProfile(String maBacSi) {
        Doctor doctor = doctorRepository.findById(maBacSi)
                .orElseThrow(() -> new RuntimeException("Không tim thấy bác sĩ " + maBacSi));
        Account account = accountRepository.findById(doctor.getTaiKhoan().getMaTaiKhoan())
                .orElseThrow(() -> new RuntimeException("Lỗi: không tìm thấy bác sĩ có tài khoản"));

        DoctorProfileDTO dto = new DoctorProfileDTO();
        dto.setMaTaiKhoan(account.getMaTaiKhoan());
        dto.setHoVaTen(account.getHoVaTen());
        dto.setEmail(account.getEmail());
        dto.setSoDienThoai(account.getSoDienThoai());
        dto.setAnhDaiDien(account.getAnhDaiDien());
        dto.setGioiTinh(account.getGioiTinh());
        dto.setNgaySinh(account.getNgaySinh());
        dto.setCccd(account.getCccd());
        dto.setDiaChi(account.getDiaChi());

        // --- THÔNG TIN CHUYÊN MÔN (Từ bảng bac_si) ---
        dto.setMaBacSi(doctor.getMaBacSi());
        dto.setChuyenKhoa(doctor.getChuyenKhoa());
        dto.setBangCap(doctor.getBangCap());
        dto.setKinhNghiem(doctor.getKinhNghiem());
        dto.setHoatDong(doctor.getHoatDong());
        dto.setMieuTa(doctor.getMieuTa());
        dto.setChucVu(doctor.getChucVu());
        dto.setHocHam(doctor.getHocHam());
        dto.setSoGiapPhep(doctor.getSoGiapPhep());
        dto.setNoiCap(doctor.getNoiCap());

        return dto;
    }

    // 2. CẬP NHẬT HỒ SƠ BÁC SĨ (PUT)
    @Transactional
    public DoctorProfileDTO updateDoctorProfile(String maBacSi, DoctorProfileDTO updateDTO) {
        Doctor doctor = doctorRepository.findById(maBacSi)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bác sĩ!"));
        Account account = accountRepository.findById(doctor.getTaiKhoan().getMaTaiKhoan())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản!"));

        // Cập nhật bảng TaiKhoan
        account.setHoVaTen(updateDTO.getHoVaTen());
        account.setSoDienThoai(updateDTO.getSoDienThoai());
        account.setGioiTinh(updateDTO.getGioiTinh());
        account.setNgaySinh(updateDTO.getNgaySinh());
        account.setCccd(updateDTO.getCccd());
        account.setDiaChi(updateDTO.getDiaChi());
        // (Lưu ý: Không nên cho đổi Email/Mã tài khoản ở đây vì nó liên quan đến định
        // danh đăng nhập)

        // Cập nhật bảng BacSi
        doctor.setChuyenKhoa(updateDTO.getChuyenKhoa());
        doctor.setBangCap(updateDTO.getBangCap());
        doctor.setKinhNghiem(updateDTO.getKinhNghiem());
        doctor.setHoatDong(updateDTO.getHoatDong());
        doctor.setMieuTa(updateDTO.getMieuTa());
        doctor.setChucVu(updateDTO.getChucVu());
        doctor.setHocHam(updateDTO.getHocHam());

        // Lưu xuống DB
        accountRepository.save(account);
        doctorRepository.save(doctor);

        // Gọi lại hàm Get để trả về data mới nhất
        return getDoctorProfile(maBacSi);
    }

}
