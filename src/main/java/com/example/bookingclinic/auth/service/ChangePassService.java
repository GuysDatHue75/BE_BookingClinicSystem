package com.example.bookingclinic.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.auth.dto.ApiResponse;
import com.example.bookingclinic.auth.dto.ChangePassDTO;
import com.example.bookingclinic.doctor.entity.Account;
import com.example.bookingclinic.doctor.repository.AccountRepository;

@Service
public class ChangePassService {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private CaptchaService captchaService;

    public ChangePassService(
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            CaptchaService captchaService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.captchaService = captchaService;
    }

    public ApiResponse changePass(ChangePassDTO infors) {

        if (infors.getIdAccount() == null ||
                infors.getOldPass() == null ||
                infors.getNewPass() == null ||
                infors.getConfirmNewPass() == null ||
                infors.getCaptcha() == null ||
                infors.getCaptchaId() == null) {
            return new ApiResponse(false, "Thiếu dữ liệu");
        }

        if (!infors.getNewPass().equals(infors.getConfirmNewPass())) {
            return new ApiResponse(false, "Mật khẩu nhập lại không khớp");
        }

        if (!captchaService.validateCaptcha(infors.getCaptchaId(), infors.getCaptcha())) {
            return new ApiResponse(false, "Captcha không đúng hoặc đã hết hạn");
        }

        Account a = accountRepository.findById(infors.getIdAccount()).orElse(null);
        if (a == null) {
            return new ApiResponse(false, "Tài khoản không tồn tại");
        }

        if (!passwordEncoder.matches(infors.getOldPass(), a.getMatKhau())) {
            return new ApiResponse(false, "Mật khẩu cũ không chính xác");
        }

        String newPass = passwordEncoder.encode(infors.getNewPass());
        a.setMatKhau(newPass);
        accountRepository.save(a);

        return new ApiResponse(true, "✅ Đổi mật khẩu thành công");
    }
}