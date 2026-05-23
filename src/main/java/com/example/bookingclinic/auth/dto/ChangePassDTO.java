package com.example.bookingclinic.auth.dto;

import lombok.Data;

@Data
public class ChangePassDTO {
    private String idAccount;
    private String oldPass;
    private String newPass;
    private String confirmNewPass;
    private String captcha;
    private String captchaId;
}
