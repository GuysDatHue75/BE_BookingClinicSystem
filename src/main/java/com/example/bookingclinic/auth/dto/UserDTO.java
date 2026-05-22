package com.example.bookingclinic.auth.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String phone;
    private String pass;
    private String otp;
}
