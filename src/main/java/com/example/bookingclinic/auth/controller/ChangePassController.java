package com.example.bookingclinic.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.bookingclinic.auth.dto.ChangePassDTO;
import com.example.bookingclinic.auth.service.ChangePassService;
import com.example.bookingclinic.auth.service.CaptchaService;
import com.example.bookingclinic.auth.dto.ApiResponse;
import com.example.bookingclinic.auth.dto.CaptchaResponseDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1")
public class ChangePassController {
    private ChangePassService changePassService;
    private CaptchaService captchaService;

    public ChangePassController(ChangePassService changePassService,
            CaptchaService captchaService) {
        this.changePassService = changePassService;
        this.captchaService = captchaService;
    }

    @GetMapping("/captcha")
    public CaptchaResponseDTO getCaptcha() throws Exception {
        return captchaService.generateCaptcha();
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody ChangePassDTO dto) {
        ApiResponse res = changePassService.changePass(dto);

        if (res.isSuccess()) {
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.badRequest().body(res);
    }
}
