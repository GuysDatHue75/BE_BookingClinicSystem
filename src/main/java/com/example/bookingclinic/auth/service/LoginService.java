package com.example.bookingclinic.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.bookingclinic.auth.dto.UserDTO;
import com.example.bookingclinic.doctor.entity.Account;
import com.example.bookingclinic.doctor.repository.AccountRepository;
import com.example.bookingclinic.doctor.repository.DoctorRepository;
import com.example.bookingclinic.doctor.repository.PatientRepository;

@Service
public class LoginService {
    private AccountRepository accountRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    // private clinicRepository clinicRepository;

    @Autowired
    // private BCryptPasswordEncoder passwordEncoder;

    private PasswordEncoder passwordEncoder;

    public LoginService(
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository) {
        // ClinicRepository clinicRepository) {
        this.accountRepository = accountRepository;
        // this.clinicRepository = clinicRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public ResponseEntity<?> login(UserDTO infors) {
        Account user = accountRepository.findBysoDt(infors.getPhone());

        if (user == null) {
            return ResponseEntity.status(404).body("Số điện thoại không tồn tại!");
        }

        // Kiểm tra mật khẩu ĐÚNG
        if (passwordEncoder.matches(infors.getPass(), user.getMatKhau())) {
            Object profileData = null;

            if ("BenhNhan".equals(user.getVaiTro())) {
                profileData = patientRepository.findByTaiKhoan_MaTaiKhoan(user.getMaTaiKhoan());
            } else if ("BacSi".equals(user.getVaiTro())) {
                profileData = doctorRepository.findByTaiKhoan_MaTaiKhoan(user.getMaTaiKhoan());
                // } else if ("PK".equals(user.getVaiTro())) {
                // profileData =
                // clinicRepository.findByAccount_MaTaiKhoan(user.getMaTaiKhoan());
            }

            if (profileData == null) {
                return ResponseEntity.status(404).body("Không tìm thấy thông tin chi tiết người dùng.");
            }

            // Trả về dữ liệu profile nếu tìm thấy
            return ResponseEntity.ok(profileData);

        } else {
            // Nhánh này mới thực sự là dành cho mật khẩu SAI
            return ResponseEntity.status(401).body("Mật khẩu không đúng!");
        }
    }
}
