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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @Column(length = 255)
    private String maTaiKhoan;
    private String soDt;
    private String matKhau;
    private String vaiTro;
    private String hoVaTen;
    private String anhDaiDien;
    private Boolean trangThai;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    private String provider;
    private String providerId;
    private String email;
    private Integer lanDauDangNhap;

    private String otpCode;
    private Date otpExpired;
    @Builder.Default
    private Boolean isDeleted = false;

}
