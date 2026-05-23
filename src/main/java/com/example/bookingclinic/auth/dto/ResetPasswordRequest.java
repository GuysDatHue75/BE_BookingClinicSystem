package com.example.bookingclinic.auth.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private String soDt;
    private String newPassword;
}
