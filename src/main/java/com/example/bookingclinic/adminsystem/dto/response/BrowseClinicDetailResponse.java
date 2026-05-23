package com.example.bookingclinic.adminsystem.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class BrowseClinicDetailResponse {
    private String maPhongKham;
    private String tenPhongKham;
    private LocalDate ngayThanhLap;
    private LocalDateTime ngayDangKy;
    private Integer soLuongBacSi;
    private String loaiHinhPhongKham;
    private String diaChi;
    private String tinhThanhPho;
    private String soDienThoai;
    private String email;
    private LocalTime gioBatDauLamViec;
    private LocalTime gioKetThucLamViec;
    private String trangThai;
    private String moTa;
    
    private String giayPhep;
    private LocalDate ngayCap;
    private String noiCap;

    private String nguoiDaiDien;
    private String soDienThoaiNguoiDaiDien;
    private String maGoi;
    private String lyDoTuChoi;
    private String anhPhongKham;
    
}
