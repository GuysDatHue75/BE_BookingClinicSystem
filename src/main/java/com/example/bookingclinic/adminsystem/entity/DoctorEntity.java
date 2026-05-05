package com.example.bookingclinic.adminsystem.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "bac_si")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorEntity {
    @Id
    @Column(name = "ma_bac_si", length = 10, nullable = false)
    private String maBacSi;

    @Column(name = "ten_bac_si", length = 100, nullable = false)
    private String tenBacSi;

    @Column(name = "gioi_tinh", nullable = false)
    private boolean gioiTinh;

    @Column(name = "so_dien_thoai", length = 50, nullable = false)
    private String soDienThoai;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "dia_chi", length = 255, nullable = false)
    private String diaChi;

    @Column(name = "avt", length = 255, nullable = false)
    private String avt;

    @Column(name = "ma_chuyen_khoa", length = 10, nullable = false)
    private String maChuyenKhoa;

    @Column(name = "bang_cap", length = 255, nullable = false)
    private String bangCap;

    @Column(name = "kinh_nghiem", length = 255, nullable = false)
    private String kinhNghiem;

    @Column(name = "hoat_dong", length = 255, nullable = false)
    private String hoatDong;

    @Column(name = "mieu_ta", length = 255)
    private String mieuTa;

    @Column(name = "chuc_vu", length = 255, nullable = false)
    private String chucVu;

    @Column(name = "hoc_ham", length = 255, nullable = false)
    private String hocHam;

    @Column(name = "cccd", length = 50, nullable = false)
    private String cccd;

    @Column(name = "so_giay_phep", length = 255, nullable = false)
    private String soGiayPhep;

    @Column(name = "ngay_cap", nullable = false)
    private LocalDateTime ngayCap;

    @Column(name = "noi_cap", length = 255, nullable = false)
    private String noiCap;

    @Column(name = "ma_tai_khoan", length= 225, nullable = false)  
    private String maTaiKhoan;

    @Column(name = "ma_phong_kham", length = 225, nullable = false)
    private String maPhongKham;

    @Column(name = "ngay_dang_ky", nullable = false)
    private LocalDateTime ngayDangKy;

    @Column(name = "tep_dinh_kem", length= 255, nullable = false)
    private String tepDinhKem;
}
