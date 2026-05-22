package com.example.bookingclinic.adminclinic.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
public class AccountEntity {
    @Id
    @Column(name = "ma_tai_khoan", length = 255, nullable = false)
    private String maTaiKhoan;

    @Column(name = "so_dt", length = 20, nullable = false)
    private String soDt;

    @Column(name = "mat_khau", length = 255, nullable = false)
    private String matKhau;

    @Column(name = "vai_tro", length = 50, nullable = false)
    private String vaiTro;

    @Column(name = "ho_va_ten", length = 255, nullable = false)
    private String hoVaTen;

    @Lob
    @Column(name = "anh_dai_dien")
    private String anhDaiDien;

    @Column(name = "trang_thai", nullable = false)
    private Boolean trangThai;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;

    @Column(name = "provider", length = 255)
    private String provider;

    @Column(name = "provider_id", length = 255)
    private String providerId;

    @Column(name = "lan_dau_dang_nhap")
    private Integer lanDauDangNhap;

    @Column(name = "otp_code", length = 10)
    private String optCode;

    @Column(name = "otp_expired")
    private LocalDateTime optExpired;

    @Column(name = "email", length = 255)
    private String email;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}
