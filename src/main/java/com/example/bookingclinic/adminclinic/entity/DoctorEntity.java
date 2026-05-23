package com.example.bookingclinic.adminclinic.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "bac_si")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorEntity {
    @Id
    @Column(name = "ma_bac_si", length = 10, nullable = false)
    private String maBacSi;

    @Column(name = "ten_bac_si", length = 100, nullable = false)
    private String tenBacSi;

    @Column(name = "gioi_tinh", nullable = false)
    private boolean gioiTinh;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Lob
    @Column(name = "que_quan")
    private String queQuan;

    @Column(name = "so_dien_thoai", length = 50, nullable = false)
    private String soDienThoai;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Lob
    @Column(name = "dia_chi")
    private String diaChi;

    @Lob
    @Column(name = "avt")
    private String avt;

    @ManyToOne @JoinColumn(name = "ma_chuyen_khoa")
    private SpecialtyEntity specialty;

    @Column(name = "bang_cap", length = 255, nullable = false)
    private String bangCap;

    @Column(name = "kinh_nghiem", length = 255, nullable = false)
    private String kinhNghiem;

    @Column(name = "hoat_dong", length = 255, nullable = false)
    private String hoatDong;

    @Lob
    @Column(name = "mieu_ta1")
    private String mieuTa1;

    @Lob
    @Column(name = "mieu_ta2")
    private String mieuTa2;

    @Column(name = "chuc_vu", length = 255, nullable = false)
    private String chucVu;

    @Column(name = "hoc_ham", length = 255, nullable = false)
    private String hocHam;

    @Column(name = "cccd", length = 50, nullable = false)
    private String cccd;

    @Column(name = "so_giay_phep", length = 255, nullable = false)
    private String soGiayPhep;

    @Column(name = "ngay_cap", nullable = false)
    private LocalDateTime ngayCap;

    @Column(name = "noi_cap", length = 255, nullable = false)
    private String noiCap;

    @OneToOne @JoinColumn(name = "ma_tai_khoan")
    private AccountEntity account;

    @ManyToOne @JoinColumn(name = "ma_phong_kham")
    private ClinicEntity clinic;

    @Column(name = "ngay_dang_ky", nullable = false)
    private LocalDateTime ngayDangKy;

    @Column(name = "tep_dinh_kem", length= 255, nullable = false)
    private String tepDinhKem;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
    
}
