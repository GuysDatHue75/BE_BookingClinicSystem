package com.example.bookingclinic.user.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "benh_nhan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UPatient {

    @Id
    @Column(name = "ma_benh_nhan")
    private String maBenhNhan;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Column(name = "que_quan")
    private String queQuan;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "nghe_nghiep")
    private String ngheNghiep;

    @Column(name = "chieu_cao")
    private Integer chieuCao;

    @Column(name = "can_nang")
    private BigDecimal canNang;

    @Column(name = "tien_su_benh_an", columnDefinition = "NVARCHAR(MAX)")
    private String tienSuBenhAn;

    @Column(name = "tinh_trang_suc_khoe", columnDefinition = "NVARCHAR(MAX)")
    private String tinhTrangSucKhoe;

    @Column(name = "nhom_mau")
    private String nhomMau;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Column(name = "email")
    private String email;

    @Column(name = "dia_chi")
    private String diaChi;

    @OneToOne
    @JoinColumn(name = "ma_tai_khoan")
    private UAccount taiKhoan;
}
