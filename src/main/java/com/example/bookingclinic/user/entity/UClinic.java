package com.example.bookingclinic.user.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "phong_kham")
@Data
public class UClinic {
    @Id
    private String maPhongKham;
    private String tenPhongKham;
    private LocalDate ngayThanhLap;
    private String diaChi;
    private String tinhThanhPho;
    private String soDienThoai;
    private String email;
    private Integer gioBatDauLamViec;
    private Integer gioKetThucLamViec;
    private String giapPhep;
    private LocalDate ngayCap;
    private String noiCap;
    private String nguoiDaiDien;
    private String soDienThoaiNguoiDaiDien;
    private String loaiHinhPhongKham;
    private String trangThai;
    @Column(name = "ma_goi_dang_ky") 
    private String maGoiDangKy; 
    private String moTa;
    private String soSao;
    private String anhPhongKham;
    @ManyToMany
    @JoinTable(
        name = "phong_kham_chuyen_khoa",
        joinColumns = @JoinColumn(name = "ma_phong_kham"),
        inverseJoinColumns = @JoinColumn(name = "ma_chuyen_khoa")
    )
    private List<Specialty> specicaltys;
    @OneToOne
    @JoinColumn(name = "ma_tai_khoan")
    private Account account;
}
