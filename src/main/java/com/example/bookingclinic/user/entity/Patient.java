package com.example.bookingclinic.user.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "benh_nhan")
public class Patient {
    @Id 
    @Column(length = 10)
    private String maBenhNhan;
    private LocalDate ngaySinh;
    private String queQuan;
    private String soDienThoai;
    private String ngheNghiep;
    private Integer chieuCao;
    private BigDecimal canNang;
    private String tienSuBenhAn;
    private String tinhTrangSucKhoe;
    private String nhomMau;
    private Boolean gioiTinh;
    private String email;
    private String diaChi;
    @OneToOne @JoinColumn(name = "ma_tai_khoan")
    private Account taiKhoan;
}
