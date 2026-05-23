// package com.example.bookingclinic.auth.service;

// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import com.example.bookingclinic.auth.dto.UserDTO;
// import com.example.bookingclinic.user.entity.Account;
// import com.example.bookingclinic.user.entity.Patient;
// import com.example.bookingclinic.user.repository.UAccountRepository;
// import com.example.bookingclinic.user.repository.UPatientRepository;

// @Service
// public class RegisterService {
//     private OTPService otpService;
//     private PasswordEncoder passwordEncoder;
//     private UAccountRepository accountRepository;
//     private UPatientRepository patientRepository;

//     public RegisterService(OTPService otpService, PasswordEncoder passwordEncoder, UAccountRepository accountRepository,
//             UPatientRepository patientRepository) {
//         this.otpService = otpService;
//         this.patientRepository = patientRepository;
//         this.accountRepository = accountRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Transactional
//     public String register(UserDTO infors) {
//         String otpCache = otpService.getOtp(infors.getPhone());
//         if (otpCache.equals(infors.getOtp())) {
//             if (accountRepository.existsBySoDt(infors.getPhone())) {
//                return "Số điện thoại đã được đăng ký trước đó!"; 
//             }
//             String encodeP = passwordEncoder.encode(infors.getPass());
//             Account a = new Account();
//             a.setMaTaiKhoan("TK" + System.currentTimeMillis());
//             a.setSoDt(infors.getPhone());
//             a.setMatKhau(encodeP);
//             a.setVaiTro("BN");
//             Account newAccount = accountRepository.save(a);
//             Patient u = new Patient();
//             u.setMaBenhNhan("BN" + System.currentTimeMillis());
//             u.setTaiKhoan(newAccount);
//             patientRepository.save(u);

//             otpService.clearOtp(infors.getPhone());
//             return "Đăng ký thành công!";
//         } 
//         return "Số điện thoại đã được đăng ký trước đó!";
//     }
// }
