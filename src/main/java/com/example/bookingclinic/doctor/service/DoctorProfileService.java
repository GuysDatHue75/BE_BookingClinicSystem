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
        public Doctor getDoctorProfile(String maBacSi) {
                return doctorRepository.findByMaBacSi(maBacSi);

        }

        // 2. CẬP NHẬT HỒ SƠ BÁC SĨ (PUT)
        @Transactional
        public DoctorProfileDTO updateDoctorProfile(String maBacSi, DoctorProfileDTO updateDTO) {
                Doctor doctor = doctorRepository.findById(maBacSi)
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ bác sĩ!"));

                // --- 1. CẬP NHẬT BẢNG TÀI KHOẢN ---
                if (doctor.getTaiKhoan() != null) {
                        Account account = doctor.getTaiKhoan();
                        account.setHoVaTen(updateDTO.getHoVaTen());
                        accountRepository.save(account);
                }

                // --- 2. CẬP NHẬT BẢNG BÁC SĨ ---
                // Thông tin cá nhân
                doctor.setSoDienThoai(updateDTO.getSoDienThoai());
                doctor.setNgaySinh(updateDTO.getNgaySinh());
                doctor.setGioiTinh(updateDTO.getGioiTinh());
                doctor.setDiaChi(updateDTO.getDiaChi());
                doctor.setCccd(updateDTO.getCccd());
                String anhBase64 = updateDTO.getAnhDaiDien();

                if (anhBase64 != null && !anhBase64.isEmpty()) {
                        // Chỉ kiểm tra và xử lý nếu đây là chuỗi Base64 mới (bắt đầu bằng data:image/)
                        if (anhBase64.startsWith("data:image/")) {

                                // Lấy dung lượng file thực tế từ chuỗi Base64
                                double sizeInBytes = (anhBase64.length() * 3.0) / 4.0;

                                // Đổi giới hạn xuống 500KB (500 * 1024 bytes) để bảo vệ database
                                if (sizeInBytes > 500 * 1024) {
                                        throw new RuntimeException(
                                                        "Kích thước ảnh quá lớn! Vui lòng chọn ảnh dưới 500KB.");
                                }

                                // Thỏa mãn điều kiện -> Gán chuỗi Base64 vào trường AVT của Doctor
                                doctor.setAvt(anhBase64);
                        } else {
                                // Nếu không bắt đầu bằng data:image/, nghĩa là người dùng giữ nguyên ảnh cũ
                                // (hoặc ảnh từ link có sẵn), ta giữ nguyên không ghi đè lỗi.
                                doctor.setAvt(anhBase64);
                        }
                }

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

                // Trả về đúng kiểu DoctorProfileDTO sau khi map dữ liệu mới nhất
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