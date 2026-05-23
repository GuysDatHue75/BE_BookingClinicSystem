package com.example.bookingclinic.auth.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.auth.dto.ForgotPasswordRequest;
import com.example.bookingclinic.auth.dto.ResetPasswordRequest;
import com.example.bookingclinic.auth.dto.VerifyOtpRequest;
import com.example.bookingclinic.user.entity.Account;
import com.example.bookingclinic.user.repository.UAccountRepository;

@Service
public class AuthService {
    @Autowired
    private UAccountRepository accountRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SmsService smsService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String generateOtp() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return String.valueOf(number);
    }

    public String sendOtpEmail(ForgotPasswordRequest request) {

        Account tk = accountRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

        String otp = generateOtp();

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MINUTE, 5);

        tk.setOtpCode(otp);
        tk.setOtpExpired(calendar.getTime());

        accountRepository.save(tk);

        emailService.sendOtp(tk.getEmail(), otp);

        return "Đã gửi OTP về email";
    }

    public String sendOtpPhone(ForgotPasswordRequest request) {

        Account tk = accountRepository
                .findBySoDt(request.getSoDt())
                .orElseThrow(() -> new RuntimeException("Số điện thoại không tồn tại"));

        String otp = generateOtp();

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MINUTE, 5);

        tk.setOtpCode(otp);
        tk.setOtpExpired(calendar.getTime());

        accountRepository.save(tk);

        smsService.sendSms(tk.getSoDt(), otp);

        return "Đã gửi OTP về số điện thoại";
    }

    public String verifyOtp(VerifyOtpRequest request) {

        Account tk = null;

        if (request.getEmail() != null) {

            tk = accountRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy email"));

        } else {

            tk = accountRepository.findBySoDt(request.getSoDt())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy số điện thoại"));
        }

        if (!tk.getOtpCode().equals(request.getOtp())) {
            throw new RuntimeException("OTP không đúng");
        }

        if (tk.getOtpExpired().before(new Date())) {
            throw new RuntimeException("OTP đã hết hạn");
        }

        return "OTP hợp lệ";
    }

    public String resetPassword(ResetPasswordRequest request) {

        Account tk = null;

        if (request.getEmail() != null) {

            tk = accountRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy email"));

        } else {

            tk = accountRepository.findBySoDt(request.getSoDt())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy số điện thoại"));
        }

        String newPasswordHash = passwordEncoder.encode(request.getNewPassword());

        tk.setMatKhau(newPasswordHash);

        tk.setOtpCode(null);

        tk.setOtpExpired(null);

        accountRepository.save(tk);

        return "Đổi mật khẩu thành công";
    }
}
