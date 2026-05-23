package com.example.bookingclinic.user.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String idToken; 
    private String password;
}
