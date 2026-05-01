package com.example.bookingclinic.adminsystem.dto.request;

import lombok.Data;

@Data
public class SubscriptionPackageSearchRequest {
    private String keyword;
    private String trangThai;
    private Integer thoiHanNgay;

    private int page = 0;
    private int size = 10;

    private String sortBy = "tenGoi";
    private String sortDirection = "asc";
    
}
