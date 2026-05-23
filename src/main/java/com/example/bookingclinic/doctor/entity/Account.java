package com.example.bookingclinic.doctor.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tai_khoan")
@Data
public class Account {

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

    @Column(name = "anh_dai_dien", columnDefinition = "NVARCHAR(MAX)")
    private String anhDaiDien;

    @Column(name = "trang_thai")
    private Boolean trangThai;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;

    @Column(name = "email")
    private String email;

    // --- CÁC TRƯỜNG MỚI BỔ SUNG TỪ SQL ---

    @Column(name = "provider")
    private String provider; // Ví dụ: google, facebook, local

    @Column(name = "provider_id")
    private String providerId;

    // Dùng Class Integer thay vì kiểu nguyên thủy int để có thể nhận giá trị null
    // từ DB
    @Column(name = "lan_dau_dang_nhap")
    private Integer lanDauDangNhap;

    @Column(name = "otp_code", length = 10)
    private String otpCode;

    @Column(name = "otp_expired")
    private LocalDateTime otpExpired;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    // --- MỐI QUAN HỆ ---

    // Trong file Account.java
    // --- MỐI QUAN HỆ ---

    @OneToOne(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    @JsonBackReference
    private Patient patient;

    @OneToOne(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    @JsonBackReference(value = "account-doctor") // Đặt tên value để tránh trùng lặp với patient
    private Doctor doctor;
}