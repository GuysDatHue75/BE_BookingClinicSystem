package com.example.bookingclinic.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.dto.UpdateImageDTO;
import com.example.bookingclinic.user.entity.UAccount;
import com.example.bookingclinic.user.service.AccountService;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PutMapping("/update-image")// tải 1 ảnh lên
    public UAccount updateProfile(@RequestBody UpdateImageDTO request) {
        UAccount updatedImageAccount = accountService.updateImage(request);
        return updatedImageAccount;
    }

    @PutMapping("/convert-status-login/{id}") // chuyển đổi lần đầu đăng nhập từ 1 - 0
    public String convertStatusLoginOne(@PathVariable String id){
        return accountService.convertStatusLoginOne(id);
    }
}
