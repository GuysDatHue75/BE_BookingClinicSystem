package com.example.bookingclinic.user.dto;

import lombok.Data;

@Data
public class IntentDTO {
    public String intent;
    public String entity;
    public String specialty;
    public String location;
    public Integer limit;
}
