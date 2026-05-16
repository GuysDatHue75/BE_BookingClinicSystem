package com.example.bookingclinic.adminsystem.dto.request;

import lombok.Data;

@Data
public class BrowseClinicSearchRequest {
    private String keyword;
    private String tinhThanhPho;
    private String nguoiDaiDien;
    private String diaChi;
    private String trangThai;
    private String loaiHinhPhongKham;
    private String maGoi;
    private int page = 0; 
    private int size = 10;
}
