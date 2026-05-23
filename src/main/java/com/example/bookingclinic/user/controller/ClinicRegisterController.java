package com.example.bookingclinic.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

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

    @PostMapping("/register-clinic") // đăng ký 1 phòng khám
    public ResponseEntity<?> registerClinic(@RequestBody ClinicRegister clinic) {
        String result = clinicRegisterService.registerClinic(clinic);

        if (result.contains("thành công")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);

    }
}
