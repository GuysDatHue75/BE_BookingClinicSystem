package com.example.bookingclinic.user.dto;

import lombok.Data;

@Data
public class IntentDTO {
    private String intent;
    private String entity;
    private String specialty;
    private String location;
    private Integer limit;
}
