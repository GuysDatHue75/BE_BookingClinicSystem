package com.example.bookingclinic.doctor.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bac_si")
@Data
public class Doctor {

    @Id
    @Column(name = "ma_bac_si", length = 10)
    private String maBacSi;

    @Column(name = "ten_bac_si", length = 255)
    private String tenBacSi;

    @Column(name = "gioi_tinh", nullable = false)
    private Boolean gioiTinh;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Column(name = "so_dien_thoai", length = 50, nullable = false)
    private String soDienThoai;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "dia_chi", columnDefinition = "NVARCHAR(MAX)")
    private String diaChi;

    @Column(name = "que_quan", columnDefinition = "NVARCHAR(MAX)")
    private String queQuan;

    @Column(name = "cccd", length = 50, nullable = false)
    private String cccd;

    @Column(name = "avt", columnDefinition = "NVARCHAR(MAX)")
    private String avt;

    // Tạm thời map dưới dạng String, nếu sếp có bảng ChuyenKhoa thì sau này đổi
    // thành @ManyToOne nhé
    @Column(name = "ma_chuyen_khoa", length = 10, nullable = false)
    private String maChuyenKhoa;

    @Column(name = "ma_phong_kham", length = 255, nullable = false)
    private String maPhongKham;

    @Column(name = "bang_cap", length = 255, nullable = false)
    private String bangCap;

    @Column(name = "kinh_nghiem", length = 255, nullable = false)
    private String kinhNghiem;

    @Column(name = "hoat_dong", length = 255, nullable = false)
    private String hoatDong;

    @Column(name = "chuc_vu", length = 255, nullable = false)
    private String chucVu;

    @Column(name = "hoc_ham", length = 255, nullable = false)
    private String hocHam;

    @Column(name = "so_giay_phep", length = 255, nullable = false)
    private String soGiayPhep;

    @Column(name = "ngay_cap", nullable = false)
    private LocalDateTime ngayCap;

    @Column(name = "noi_cap", length = 255, nullable = false)
    private String noiCap;

    @Column(name = "mieu_ta1", columnDefinition = "NVARCHAR(MAX)")
    private String mieuTa1;

    @Column(name = "mieu_ta2", columnDefinition = "NVARCHAR(MAX)")
    private String mieuTa2;

    @Column(name = "tep_dinh_kem", columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String tepDinhKem;

    @Column(name = "ngay_dang_ky")
    private LocalDateTime ngayDangky;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;


    @OneToOne
    @JoinColumn(name = "ma_tai_khoan")
    @JsonManagedReference
    private Account taiKhoan;
}

