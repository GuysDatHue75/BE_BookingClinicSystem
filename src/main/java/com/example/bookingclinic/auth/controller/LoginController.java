package com.example.bookingclinic.auth.controller;

import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.CrossOrigin;
>>>>>>> develop
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.auth.dto.UserDTO;
import com.example.bookingclinic.auth.service.LoginService;

@RestController
@RequestMapping("/api/v1")
<<<<<<< HEAD
public class LoginController {
    private LoginService loginService;
    public LoginController(LoginService loginService){
=======
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LoginController {
    private LoginService loginService;

    public LoginController(LoginService loginService) {
>>>>>>> develop
        this.loginService = loginService;
    }

    @PostMapping("/login") // đăng nhập
<<<<<<< HEAD
    public ResponseEntity<?> login(@RequestBody UserDTO infors){
=======
    public ResponseEntity<?> login(@RequestBody UserDTO infors) {
>>>>>>> develop
        return loginService.login(infors);
    }
}
