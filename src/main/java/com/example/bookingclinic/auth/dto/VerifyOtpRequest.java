package com.example.bookingclinic.auth.dto;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String email;
    private String soDt;
    private String otp;
}
