package com.example.bookingclinic.adminsystem.dto.request;

import lombok.Data;

@Data
public class AccountSearchRequest {
    private String keyword;     
    private String vaiTro;      
    private int page = 0;       
    private int size = 10;
}
