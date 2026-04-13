package com.example.bookingclinic.adminsystem.dto.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BrowseDoctorSearchRequest {
    private String keyword;
    private String soDienThoai;
    private String diaChi;
    private String trangThai;
    private String chuyenKhoa;
    private String chucVu;
    private String hocHam;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
