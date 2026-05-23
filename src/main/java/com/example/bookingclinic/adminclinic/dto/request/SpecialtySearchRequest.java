package com.example.bookingclinic.adminclinic.dto.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SpecialtySearchRequest {
    private String keyword;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

}
