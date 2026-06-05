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

        // 1. LẤY HỒ SƠ BÁC SĨ (GET)
        public DoctorProfileDTO getDoctorProfile(String maBacSi) {
                Doctor doctor = doctorRepository.findByMaBacSi(maBacSi);
                if (doctor == null) {
                        throw new RuntimeException("Không tìm thấy hồ sơ bác sĩ!");
                }
                // Gọi hàm mapToDTO có sẵn để biến đổi tên trường avt -> anhDaiDien
                return mapToDTO(doctor);
        }

        // 2. CẬP NHẬT HỒ SƠ BÁC SĨ (PUT)
        @Transactional
        public DoctorProfileDTO updateDoctorProfile(String maBacSi, DoctorProfileDTO updateDTO) {
                Doctor doctor = doctorRepository.findById(maBacSi)
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ bác sĩ!"));

                // 1. LẤY VÀ KIỂM TRA ẢNH TRƯỚC TIÊN
                // Khai báo biến ở đây để dùng chung cho cả Account và Doctor phía dưới
                String anhBase64 = updateDTO.getAnhDaiDien();

                if (anhBase64 != null && !anhBase64.isEmpty()) {
                        if (anhBase64.startsWith("data:image/")) {
                                double sizeInBytes = (anhBase64.length() * 3.0) / 4.0;
                                if (sizeInBytes > 500 * 1024) {
                                        throw new RuntimeException(
                                                        "Kích thước ảnh quá lớn! Vui lòng chọn ảnh dưới 500KB.");
                                }
                        }
                }

                // --- 2. CẬP NHẬT BẢNG TÀI KHOẢN ---
                if (doctor.getTaiKhoan() != null) {
                        Account account = doctor.getTaiKhoan();
                        account.setHoVaTen(updateDTO.getHoVaTen());
                        if (anhBase64 != null && !anhBase64.trim().isEmpty()) {
                                account.setAnhDaiDien(anhBase64);
                        }
                        accountRepository.save(account);

                        // Bây giờ biến anhBase64 đã tồn tại và sẵn sàng được gán
                        accountRepository.save(account);

                }

                // --- 3. CẬP NHẬT BẢNG BÁC SĨ ---
                // Thông tin cá nhân
                if (anhBase64 != null && !anhBase64.trim().isEmpty()) {
                        doctor.setAvt(anhBase64);
                }
                doctor.setSoDienThoai(updateDTO.getSoDienThoai());
                doctor.setNgaySinh(updateDTO.getNgaySinh());
                doctor.setGioiTinh(updateDTO.getGioiTinh());
                doctor.setDiaChi(updateDTO.getDiaChi());
                doctor.setCccd(updateDTO.getCccd());

                // Thông tin chuyên môn
                doctor.setMaChuyenKhoa(updateDTO.getMaChuyenKhoa());
                doctor.setBangCap(updateDTO.getBangCap());
                doctor.setKinhNghiem(updateDTO.getKinhNghiem());
                doctor.setHoatDong(updateDTO.getHoatDong());
                doctor.setMieuTa1(updateDTO.getMieuTa1());
                doctor.setMieuTa2(updateDTO.getMieuTa2());
                doctor.setChucVu(updateDTO.getChucVu());
                doctor.setHocHam(updateDTO.getHocHam());

                // Giấy phép hành nghề
                doctor.setSoGiayPhep(updateDTO.getSoGiayPhep());
                doctor.setNoiCap(updateDTO.getNoiCap());
                doctor.setNgayCap(updateDTO.getNgayCap());

                // Lưu xuống DB
                Doctor savedDoctor = doctorRepository.save(doctor);

                // Trả về DTO
                return mapToDTO(savedDoctor);
        }

        // Hàm phụ trợ giúp chuyển đổi Entity sang DTO nhanh gọn
        private DoctorProfileDTO mapToDTO(Doctor doctor) {
                DoctorProfileDTO dto = new DoctorProfileDTO();

                if (doctor.getTaiKhoan() != null) {
                        Account account = doctor.getTaiKhoan();
                        dto.setMaTaiKhoan(account.getMaTaiKhoan());
                        dto.setHoVaTen(account.getHoVaTen());
                }

                dto.setMaBacSi(doctor.getMaBacSi());
                dto.setEmail(doctor.getEmail());
                dto.setSoDienThoai(doctor.getSoDienThoai());
                dto.setAnhDaiDien(doctor.getAvt());
                dto.setNgaySinh(doctor.getNgaySinh());
                dto.setGioiTinh(doctor.getGioiTinh());
                dto.setDiaChi(doctor.getDiaChi());
                dto.setCccd(doctor.getCccd());
                dto.setMaChuyenKhoa(doctor.getMaChuyenKhoa());
                dto.setBangCap(doctor.getBangCap());
                dto.setKinhNghiem(doctor.getKinhNghiem());
                dto.setHoatDong(doctor.getHoatDong());
                dto.setMieuTa1(doctor.getMieuTa1());
                dto.setMieuTa2(doctor.getMieuTa2());
                dto.setChucVu(doctor.getChucVu());
                dto.setHocHam(doctor.getHocHam());
                dto.setSoGiayPhep(doctor.getSoGiayPhep());
                dto.setNgayCap(doctor.getNgayCap());
                dto.setNoiCap(doctor.getNoiCap());

                return dto;
        }
}