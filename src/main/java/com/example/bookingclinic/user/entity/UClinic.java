package com.example.bookingclinic.user.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "phong_kham")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UClinic {

    @Id
    private String maPhongKham;
    private String tenPhongKham;
    private LocalDate ngayThanhLap;
    private String diaChi;
    private String tinhThanhPho;
    private String soDienThoai;
    private String email;
    private LocalTime gioBatDauLamViec;
    private LocalTime gioKetThucLamViec;
    private String giayPhep;
    private LocalDate ngayCap;
    private String noiCap;
    private String nguoiDaiDien;
    private String soDienThoaiNguoiDaiDien;
    private String loaiHinhPhongKham;
    private Integer soLuongBacSi;
    private LocalDateTime ngayHetHan;
    private LocalDateTime ngayDangKy;
    private String trangThai;
    @JoinColumn(name = "ma_goi") 
    @OneToOne
    private Package packagee; 

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

    @Builder.Default
    private Boolean isDeleted = false;
}
