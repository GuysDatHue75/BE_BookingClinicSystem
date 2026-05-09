package com.example.bookingclinic.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.entity.ClinicRegister;
import com.example.bookingclinic.user.service.ClinicRegisterService;

@RestController
@RequestMapping("/api/v1")
public class ClinicRegisterController {
    @Autowired
    private ClinicRegisterService clinicRegisterService;

    @PostMapping("/register-clinic") // đăng ký 1 phòng khám mới
    public String registerClinic(@RequestBody ClinicRegister clinic) {
        return clinicRegisterService.registerClinic(clinic);
    }
}
