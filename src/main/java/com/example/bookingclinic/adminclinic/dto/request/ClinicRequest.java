package com.example.bookingclinic.adminclinic.dto.request;

import java.time.LocalDate;
// import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClinicRequest {
    private String tenPhongKham;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ngayThanhLap;
    // private LocalDateTime ngayDangKy;
    // private Integer soLuongBacSi;
    private String loaiHinhPhongKham;
    private String diaChi;
    private String tinhThanhPho;
    private String soDienThoai;
    private String email;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime gioBatDauLamViec;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime gioKetThucLamViec;
    // private String trangThai;
    private String moTa;
    
    // private String giayPhep;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ngayCap;
    private String noiCap;

    private String nguoiDaiDien;
    private String soDienThoaiNguoiDaiDien;
}