package com.example.bookingclinic.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.bookingclinic.auth.dto.UserDTO;
import com.example.bookingclinic.user.entity.Account;
import com.example.bookingclinic.user.repository.AccountRepository;
import com.example.bookingclinic.user.repository.ClinicRepository;
import com.example.bookingclinic.user.repository.DoctorReponsitory;
import com.example.bookingclinic.user.repository.PatientRepository;

@Service
public class LoginService {
    private AccountRepository accountRepository;
    private PatientRepository patientRepository;
    private DoctorReponsitory doctorRepository;
    private ClinicRepository clinicRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public LoginService(
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            PatientRepository patientRepository,
            DoctorReponsitory doctorRepository,
            ClinicRepository clinicRepository) {
        this.accountRepository = accountRepository;
        this.clinicRepository = clinicRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public ResponseEntity<?> login(UserDTO infors) {
        Account user = accountRepository.findBySoDt(infors.getPhone());
        if (user == null) {
            return ResponseEntity.status(404).body("Số điện thoại không tồn tại!");
        }
        if (passwordEncoder.matches(infors.getPass(), user.getMatKhau())) {
            Object profileData = null;
            if ("BN".equals(user.getVaiTro())) {
                profileData = patientRepository.findByTaiKhoan_MaTaiKhoan(user.getMaTaiKhoan());
            } else if ("BS".equals(user.getVaiTro())) {
                profileData = doctorRepository.findByTaiKhoan_MaTaiKhoan(user.getMaTaiKhoan());
            } else if ("PK".equals(user.getVaiTro())) {
                profileData = clinicRepository.findByAccount_MaTaiKhoan(user.getMaTaiKhoan());
            }
            if (profileData == null) {
                return ResponseEntity.status(404).body("Không tìm thấy thông tin chi tiết người dùng.");
            }
            return ResponseEntity.ok(profileData);
        } else {
            return ResponseEntity.status(401).body("Mật khẩu không đúng!");
        }
    }

}
