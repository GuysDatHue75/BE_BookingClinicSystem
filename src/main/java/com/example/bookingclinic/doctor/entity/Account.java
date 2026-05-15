package com.example.bookingclinic.doctor.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tai_khoan")
@Data
public class Account {
    @Id
    @Column(name = "ma_tai_khoan", length = 10)
    private String maTaiKhoan;
    private String matKhau;
    private String vaiTro;
    private String hoVaTen;
    private String anhDaiDien;
    private Boolean trangThai;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    private String soDienThoai;
    private Boolean gioiTinh;
    private LocalDate ngaySinh;
    private String cccd;
    private String diaChi;
    private String email;

    @OneToOne(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    @JsonBackReference
    private Patient patient;

    @OneToOne(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    private Doctor doctor;
}