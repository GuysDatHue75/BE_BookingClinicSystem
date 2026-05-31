package com.example.bookingclinic.adminclinic.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClinicResponse {
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
    private String anhPhongKham;
    private String soSao;

    private String maTaiKhoan;
    private String soDt;
    private String matKhau;

    private String nguoiDaiDien;
    private String soDienThoaiNguoiDaiDien;
    private String maGoi;
    private String tenGoi;
    private LocalDateTime ngayHetHan;
}
