package com.example.bookingclinic.user.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.example.bookingclinic.user.dto.RegisterRequest;
import com.example.bookingclinic.user.entity.UAccount;
import com.example.bookingclinic.user.entity.UPatient;
import com.example.bookingclinic.user.repository.UAccountRepository;
import com.example.bookingclinic.user.repository.UPatientRepository;

@RestController
@RequestMapping("/api/v1")

public class RegisterController {
    @Autowired
    private UAccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UPatientRepository patientRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        try {
            // 1. Xác thực Token gửi từ React với Firebase
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(request.getIdToken());

            // 2. Lấy số điện thoại đã được Firebase xác thực thành công
            String phoneClaim = (String) decodedToken.getClaims().get("phone_number");

            String phone = null;
            if (phoneClaim != null) {
                phone = phoneClaim.replace("+84", "0");
            }

            if (accountRepository.existsBySoDt(phone)) {
                System.out.println("Phone đã trùng" + phone);
                return ResponseEntity.badRequest().body("Số điện thoại đã được đăng ký.");
            }
            UAccount newUser = new UAccount();
            String id = "TK" + System.currentTimeMillis();
            newUser.setMaTaiKhoan(id);
            newUser.setSoDt(phone);
            newUser.setVaiTro("BenhNhan");

            newUser.setHoVaTen("BN" + System.currentTimeMillis());
            newUser.setProvider("CREATE");
            newUser.setLanDauDangNhap(1);
            newUser.setNgayTao(LocalDateTime.now());
            newUser.setMatKhau(passwordEncoder.encode(request.getPassword()));
            accountRepository.save(newUser);

            UPatient newPatient = new UPatient();
            newPatient.setMaBenhNhan("BN" + System.currentTimeMillis());
            newPatient.setTaiKhoan(newUser);
            newPatient.setSoDienThoai(phone);
            patientRepository.save(newPatient);
            return ResponseEntity.ok("Đăng ký thành công với số: " + phone);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Xác thực thất bại hoặc Token hết hạn: " + e.getMessage());
        }
    }
}
