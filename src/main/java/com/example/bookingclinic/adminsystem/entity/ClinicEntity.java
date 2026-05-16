package com.example.bookingclinic.adminsystem.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "phong_kham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClinicEntity {
    @Id
    @Column(name = "ma_phong_kham", length = 225, nullable = false)
    private String maPhongKham;

    @Column(name = "ten_phong_kham", length = 255, nullable = false)
    private String tenPhongKham;

    @Column(name = "ngay_thanh_lap", nullable = false)
    private LocalDateTime ngayThanhLap;

    @Column(name = "ngay_dang_ky", nullable = false)
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

    @Column(name = "ngay_cap", nullable = false)
    private LocalDateTime ngayCap;

    @Column(name = "noi_cap", length = 255, nullable = false)
    private String noiCap;

    @Column(name = "tep_dinh_kem", length= 255, nullable = false)
    private String tepDinhKem;

    @Column(name = "nguoi_dai_dien", length= 255, nullable = false)
    private String nguoiDaiDien;

    @Column(name = "so_dien_thoai_nguoi_dai_dien", length= 50, nullable = false)
    private String soDienThoaiNguoiDaiDien;

    @Column(name = "loai_hinh_phong_kham", length= 255, nullable = false)
    private String loaiHinhPhongKham;

    @Column(name = "trang_thai", length= 255, nullable = false)
    private String trangThai;

    @Column(name = "ma_goi", length= 10, nullable = false)
    private String maGoi;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "ngay_het_han")
    private LocalDateTime ngayHetHan;

    @Column(name = "ma_tai_khoan", length = 225, nullable = false)
    private String maTaiKhoan;

    @Column(name = "anh_phong_kham", length = 255, nullable = false)
    private String anhPhongKham;

}
