package com.example.bookingclinic.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.repository.UAccountRepository;

@RestController
@RequestMapping("/api/v1")
public class CheckPhoneController {
    @Autowired
    private UAccountRepository uAccountRepository;
    @GetMapping("/check-phone")
    public ResponseEntity<?> checkPhoneExists(@RequestParam String phone) {
        boolean exists = uAccountRepository.existsBySoDt(phone);

        // Trả về một object JSON: { "exists": true / false }
        return ResponseEntity.ok(Map.of("exists", exists));
    }
}
