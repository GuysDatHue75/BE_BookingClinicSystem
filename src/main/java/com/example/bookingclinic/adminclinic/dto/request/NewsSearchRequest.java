package com.example.bookingclinic.adminclinic.dto.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NewsSearchRequest {
    private String keyword;
    
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    private int page = 0;
    private int size = 10;

    private String sortBy = "thoiGianGui";
    private String sortDirection = "desc";
}
