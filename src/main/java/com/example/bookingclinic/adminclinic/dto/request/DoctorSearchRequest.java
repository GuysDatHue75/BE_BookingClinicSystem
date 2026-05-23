package com.example.bookingclinic.adminclinic.dto.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DoctorSearchRequest {
    private String tenBacSi;
    private String maChuyenKhoa;
    private String soDienThoai;
    private String diaChi;
    private String kinhNghiem;
    private String chucVu;
    private String hocHam;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
