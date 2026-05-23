package com.example.bookingclinic.user.entity;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tai_khoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UAccount {

    @Id
    @Column(name = "ma_tai_khoan", length = 255)
    private String maTaiKhoan;

    @Column(name = "so_dt")
    private String soDt;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "vai_tro")
    private String vaiTro;

    @Column(name = "ho_va_ten")
    private String hoVaTen;

    @Column(name = "anh_dai_dien")
    private String anhDaiDien;

    @Column(name = "trang_thai")
    private Boolean trangThai = true;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "email")
    private String email;

    @Column(name = "lan_dau_dang_nhap")
    private Integer lanDauDangNhap;

    @Column(name = "otp_code")
    private String otpCode;

    @Column(name = "otp_expired")
    private Date otpExpired;

    @Builder.Default
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
}

