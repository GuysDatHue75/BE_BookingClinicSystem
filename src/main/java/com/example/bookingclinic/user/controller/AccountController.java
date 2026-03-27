package com.example.bookingclinic.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.dto.UpdateImageDTO;
import com.example.bookingclinic.user.entity.Account;
import com.example.bookingclinic.user.service.AccountService;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PutMapping("/update-image")
    public Account updateProfile(@RequestBody UpdateImageDTO request) {
        Account updatedImageAccount = accountService.updateImage(request);
        return updatedImageAccount;
    }

}
