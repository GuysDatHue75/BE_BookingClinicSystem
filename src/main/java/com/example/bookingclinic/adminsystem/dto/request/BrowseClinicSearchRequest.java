package com.example.bookingclinic.adminsystem.dto.request;

import lombok.Data;

@Data
public class BrowseClinicSearchRequest {
    private String keyword;
    private String tinhThanhPho;
    private String trangThai;
    private String loaiHinhPhongKham;
    private int page = 0; 
    private int size = 10;
}
