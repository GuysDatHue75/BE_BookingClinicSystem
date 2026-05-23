package com.example.bookingclinic.user.entity;

import java.sql.Time;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "duyet_phong_kham")
public class ClinicRegister {

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
    private Time gioBatDauLamViec;

    @Column(name = "gio_ket_thuc_lam_viec")
    private Time gioKetThucLamViec;

    @Column(name = "giay_phep")
    private String giayPhep;

    @Column(name = "ngay_cap")
    private LocalDate ngayCap;

    @Column(name = "anh_phong_kham")
    private String anhPhongKham;

    @Column(name = "noi_cap")
    private String noiCap;

    @Column(name = "nguoi_dai_dien")
    private String nguoiDaiDien;

    @Column(name = "so_dien_thoai_nguoi_dai_dien")
    private String soDienThoaiNguoiDaiDien;

    @Column(name = "loai_hinh_phong_kham")
    private String loaiHinhPhongKham;

    @Column(name = "trang_thai")
    private String trangThai;

    @Column(name = "ma_goi")
    private String maGoi;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "ly_do_tu_choi")
    private String lyDoTuChoi;

    @Column(name = "so_luong_bac_si")
    private int soLuongBacSi;

    @Column(name = "ngay_dang_ky")
    private LocalDate ngayDangKy;
}