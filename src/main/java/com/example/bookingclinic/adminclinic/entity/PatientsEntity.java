package com.example.bookingclinic.adminclinic.entity;

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
public class PatientsEntity {
    @Id
    @Column(name = "ma_benh_nhan", length = 255, nullable = false)
    private String maBenhNhan;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Column(name = "que_quan", length = 255)
    private String queQuan;

    @Column(name = "so_dien_thoai", length = 50)
    private String soDienThoai;

    @Column(name = "nghe_nghiep", length = 255)
    private String ngheNghiep;

    @Column(name = "chieu_cao")
    private int chieuCao;

    @Column(name = "can_nang")
    private float canNang;

    @Column(name = "tien_su_benh_an", length = 255)
    private String tienSuBenhAn;

    @Column(name = "tinh_trang_suc_khoe", length = 255)
    private String tinhTrangSucKhoe;

    @Column(name = "nhom_mau", length = 10)
    private String nhomMau;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "dia_chi", length = 255)
    private String diaChi;

    @OneToOne @JoinColumn(name = "ma_tai_khoan")
    private AccountEntity account;

}
