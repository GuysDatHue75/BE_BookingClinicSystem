package com.example.bookingclinic.user.entity;

import java.sql.Time;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "duyet_phong_kham")
@Entity
public class ClinicRegister {
    @Id
    private String maPhongKham;
    private String maChuyenKhoa;
    private String tenPhongKham;
    private LocalDate ngayThanhLap;
    private String diaChi;
    private String tinhThanhPho;
    private String soDienThoai;
    private String email;
    private Time gioBatDauLamViec;
    private Time gioKetThucLamViec;
    private String giapPhep;
    private LocalDate ngayCap;
    private String noiCap;
    private String nguoiDaiDien;
    private String soDienThoaiNguoiDaiDien;
    private String loaiHinhPhongKham;
    private String trangThai;
    private String tepDinhKep;
    private String maGoi; 
    private String moTa;
    private String lyDoTuChoi;
    private int soLuongBacSi;
    private LocalDate ngayDangKy;
}
