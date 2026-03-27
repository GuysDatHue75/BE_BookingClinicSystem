package com.example.bookingclinic.auth.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.auth.dto.UserDTO;
import com.example.bookingclinic.auth.service.OTPService;
import com.example.bookingclinic.auth.service.RegisterService;

@RestController
@RequestMapping("/api/v1")
public class RegisterController {
    private OTPService otpService;
    private RegisterService registerService;
    public RegisterController(OTPService otpService, RegisterService registerService){
        this.otpService = otpService;
        this.registerService = registerService;
    }
    @PostMapping("/otp") // lấy mã otp
    public Map<String,String> renderOTP(@RequestBody UserDTO infor){
        return otpService.renderOTP(infor.getPhone());
    }

    @PostMapping("/register") // đăng ký
    public String register(@RequestBody UserDTO infor){
        return registerService.register(infor);
    }
}
