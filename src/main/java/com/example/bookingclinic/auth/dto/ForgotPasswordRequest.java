package com.example.bookingclinic.auth.dto;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    
    private String email;
    private String soDt;
}
