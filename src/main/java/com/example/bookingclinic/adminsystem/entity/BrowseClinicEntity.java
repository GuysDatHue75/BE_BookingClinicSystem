package com.example.bookingclinic.adminsystem.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "duyet_phong_kham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrowseClinicEntity {
    @Id
    @Column(name = "ma_phong_kham", length = 225, nullable = false)
    private String maPhongKham;

    @Column(name = "ten_phong_kham", length = 255, nullable = false)
    private String tenPhongKham;

    @Column(name = "ngay_thanh_lap")
    private LocalDate ngayThanhLap;

    @Column(name = "ngay_dang_ky")
    private LocalDateTime ngayDangKy;

    @Column(name = "so_luong_bac_si", nullable = false)
    private Integer soLuongBacSi;

    @Lob
    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "dia_chi", length = 255, nullable = false)
    private String diaChi;

    @Column(name = "tinh_thanh_pho", length = 255, nullable = false)
    private String tinhThanhPho;

    @Column(name = "so_dien_thoai", length = 50, nullable = false)
    private String soDienThoai;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "gio_bat_dau_lam_viec", nullable = false)
    private LocalTime gioBatDauLamViec;

    @Column(name = "gio_ket_thuc_lam_viec", nullable = false)
    private LocalTime gioKetThucLamViec;

    @Column(name = "giay_phep", length = 255, nullable = false)
    private String giayPhep;

    @Column(name = "ngay_cap")
    private LocalDate ngayCap;

    @Column(name = "noi_cap", length = 255, nullable = false)
    private String noiCap;

    @Column(name = "nguoi_dai_dien", length= 255, nullable = false)
    private String nguoiDaiDien;

    @Column(name = "so_dien_thoai_nguoi_dai_dien", length= 50, nullable = false)
    private String soDienThoaiNguoiDaiDien;

    @Column(name = "loai_hinh_phong_kham", length= 255, nullable = false)
    private String loaiHinhPhongKham;

    @Column(name = "trang_thai", length= 255, nullable = false)
    private String trangThai;

    @ManyToOne @JoinColumn(name = "ma_goi")
    private SubscriptionPackageEntity subpackage;

    @Column(name = "ly_do_tu_choi", length= 255)
    private String lyDoTuChoi;

    @Lob
    @Column(name = "anh_phong_kham")
    private String anhPhongKham;
}
