package com.example.bookingclinic.auth.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.bookingclinic.auth.dto.UserDTO;
import com.example.bookingclinic.adminsystem.entity.AccountEntity;
// import com.example.bookingclinic.user.entity.Account;
// import com.example.bookingclinic.user.repository.AccountRepository;
// import com.example.bookingclinic.user.repository.ClinicRepository;
// import com.example.bookingclinic.user.repository.DoctorReponsitory;
import com.example.bookingclinic.adminsystem.repository.AccountRepository;
import com.example.bookingclinic.adminsystem.repository.ClinicRepository;
import com.example.bookingclinic.adminsystem.repository.DoctorRepository;
import com.example.bookingclinic.user.repository.PatientRepository;

@Service
public class LoginService {
    private AccountRepository accountRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    private ClinicRepository clinicRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public LoginService(
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            ClinicRepository clinicRepository) {
        this.accountRepository = accountRepository;
        this.clinicRepository = clinicRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public ResponseEntity<?> login(UserDTO infors) {
        // AccountEntity user = accountRepository.findBySoDt(infors.getPhone())
        //     .orElseThrow(() -> new RuntimeException("Không tìm thấy sô đt"));
        // if (user == null) {
        //     return ResponseEntity.status(404).body("Số điện thoại không tồn tại!");
        // }
        // if (passwordEncoder.matches(infors.getPass(), user.getMatKhau())) {
        Optional<AccountEntity> optionalUser = accountRepository.findBySoDt(infors.getPhone());
        
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404)
                .body("Số điện thoại không tồn tại!");
        }

        AccountEntity user = optionalUser.get();

        if(infors.getPass().equals(user.getMatKhau())){
            System.out.print(infors.getPhone());
            Object profileData = null;
            if ("BenhNhan".equals(user.getVaiTro())) {
                profileData = patientRepository.findByTaiKhoan_MaTaiKhoan(user.getMaTaiKhoan());
            } else if ("BacSi".equals(user.getVaiTro())) {
                profileData = doctorRepository.findByAccount_MaTaiKhoan(user.getMaTaiKhoan());
            } else if ("PhongKham".equals(user.getVaiTro())) {
                profileData = clinicRepository.findByAccount_MaTaiKhoan(user.getMaTaiKhoan());
            } else if ("Admin".equals(user.getVaiTro())){
                Map<String, Object> adminData = new java.util.HashMap<>();
                adminData.put("taiKhoan", user);
                adminData.put("queQuan", "");
                adminData.put("maBenhNhan", "");
                profileData = adminData;
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
