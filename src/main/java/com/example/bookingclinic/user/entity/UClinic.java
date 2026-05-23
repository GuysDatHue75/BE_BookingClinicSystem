package com.example.bookingclinic.user.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Column;
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
    @Column(name = "ma_phong_kham", length = 255)
    private String maPhongKham;

    @Column(name = "ten_phong_kham")
    private String tenPhongKham;

    @Column(name = "ngay_thanh_lap")
    private LocalDate ngayThanhLap;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "tinh_thanh_pho")
    private String tinhThanhPho;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

    @Column(name = "gio_bat_dau_lam_viec")
    private LocalTime gioBatDauLamViec;

    @Column(name = "gio_ket_thuc_lam_viec")
    private LocalTime gioKetThucLamViec;

    @Column(name = "giay_phep")
    private String giayPhep;

    @Column(name = "ngay_cap")
    private LocalDate ngayCap;

    @Column(name = "noi_cap")
    private String noiCap;

    @Column(name = "nguoi_dai_dien")
    private String nguoiDaiDien;

    @Column(name = "so_dien_thoai_nguoi_dai_dien")
    private String soDienThoaiNguoiDaiDien;

    @Column(name = "loai_hinh_phong_kham")
    private String loaiHinhPhongKham;

    @Column(name = "so_luong_bac_si")
    private Integer soLuongBacSi;

    @Column(name = "ngay_het_han")
    private LocalDateTime ngayHetHan;

    @Column(name = "ngay_dang_ky")
    private LocalDateTime ngayDangKy;

    @Column(name = "trang_thai")
    private String trangThai;

    @OneToOne
    @JoinColumn(name = "ma_goi")
    private Package packagee;

    @Column(name = "mo_ta", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;

    @Column(name = "so_sao")
    private String soSao;

    @Column(name = "anh_phong_kham")
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
    private UAccount account;

    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = false;
}