package com.example.bookingclinic.auth.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.bookingclinic.auth.dto.UserDTO;

import com.example.bookingclinic.adminclinic.entity.AccountEntity;
import com.example.bookingclinic.adminclinic.repository.ClinicAccountRepository;
import com.example.bookingclinic.adminclinic.repository.ClinicDoctorRepository;
import com.example.bookingclinic.adminclinic.repository.ClinicPatientsRepository;
import com.example.bookingclinic.adminclinic.repository.ClinicRepository;

// import com.example.bookingclinic.user.entity.Account;
// import com.example.bookingclinic.user.repository.AccountRepository;
// import com.example.bookingclinic.user.repository.ClinicRepository;
// import com.example.bookingclinic.user.repository.DoctorReponsitory;

// import com.example.bookingclinic.doctor.entity.Account;
// import com.example.bookingclinic.doctor.repository.AccountRepository;
// import com.example.bookingclinic.doctor.repository.DoctorRepository;
// import com.example.bookingclinic.doctor.repository.PatientRepository;

@Service
public class LoginService {
    private ClinicAccountRepository accountRepository;
    private ClinicPatientsRepository patientsRepository;
    private ClinicDoctorRepository doctorRepository;
    private ClinicRepository clinicRepository;

    
    private BCryptPasswordEncoder passwordEncoder;
    public LoginService(
            ClinicAccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            ClinicPatientsRepository patientsRepository,
            ClinicDoctorRepository doctorRepository,
            ClinicRepository clinicRepository) {
        this.accountRepository = accountRepository;
        this.clinicRepository = clinicRepository;
        this.doctorRepository = doctorRepository;
        this.patientsRepository = patientsRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public ResponseEntity<?> login(UserDTO infors) {
        // AccountEntity user = accountRepository.findBySoDt(infors.getPhone())
        //     .orElseThrow(() -> new RuntimeException("Không tìm thấy sô đt"));
        // if (user == null) {
        //     return ResponseEntity.status(404).body("Số điện thoại không tồn tại!");
        // }
        // if (passwordEncoder.matches(infors.getPass(), user.getMatKhau())) {
        String phoneToSearch = infors.getPhone().trim(); // kiểm tra fix lỗi
        System.out.println(">>> ĐANG TÌM SĐT: [" + phoneToSearch + "]");
        Optional<AccountEntity> optionalUser = accountRepository.findBySoDt(phoneToSearch);
        
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404)
                .body("Số điện thoại không tồn tại!");
        }

        AccountEntity user = optionalUser.get();

        if(infors.getPass().equals(user.getMatKhau())){
            System.out.print(infors.getPhone());
            Object profileData = null;
            if ("BenhNhan".equals(user.getVaiTro())) {
                profileData = patientsRepository.findByAccount_MaTaiKhoan(user.getMaTaiKhoan());
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
