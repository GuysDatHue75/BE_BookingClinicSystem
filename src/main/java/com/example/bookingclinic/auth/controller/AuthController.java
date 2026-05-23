package com.example.bookingclinic.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.auth.dto.ForgotPasswordRequest;
import com.example.bookingclinic.auth.dto.ResetPasswordRequest;
import com.example.bookingclinic.auth.dto.VerifyOtpRequest;
import com.example.bookingclinic.auth.service.AuthService;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    @Autowired
    private AuthService authService;

    // gửi otp email
    @PostMapping("/forgot-password/email")
    public String forgotPasswordEmail(
            @RequestBody ForgotPasswordRequest request
    ) {

        return authService.sendOtpEmail(request);
    }

    // gửi otp phone
    @PostMapping("/forgot-password/phone")
    public String forgotPasswordPhone(
            @RequestBody ForgotPasswordRequest request
    ) {

        return authService.sendOtpPhone(request);
    }

    // verify otp
    @PostMapping("/verify-otp")
    public String verifyOtp(
            @RequestBody VerifyOtpRequest request
    ) {

        return authService.verifyOtp(request);
    }

    // reset password
    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {

        return authService.resetPassword(request);
    }
}
