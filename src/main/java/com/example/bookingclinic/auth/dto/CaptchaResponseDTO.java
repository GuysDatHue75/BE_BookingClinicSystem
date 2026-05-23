package com.example.bookingclinic.auth.dto;

import lombok.Data;

@Data
public class CaptchaResponseDTO {
    private String captchaId;
    private String imageBase64;
     public CaptchaResponseDTO(String captchaId, String imageBase64) {
        this.captchaId = captchaId;
        this.imageBase64 = imageBase64;
    }
}
