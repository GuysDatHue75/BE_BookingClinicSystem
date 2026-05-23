package com.example.bookingclinic.user.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bac_si")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UDoctor {

    @Id
    @Column(name = "ma_bac_si", length = 255)
    private String maBacSi;

    @Column(name = "ten_bac_si")
    private String tenBacSi;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

    @Column(name = "que_quan")
    private String queQuan;

    @Column(name = "avt")
    private String avt;

    @OneToOne
    @JoinColumn(name = "ma_chuyen_khoa")
    private Specialty specialty;

    @Column(name = "bang_cap")
    private String bangCap;

    @Column(name = "kinh_nghiem", columnDefinition = "NVARCHAR(MAX)")
    private String kinhNghiem;

    @Column(name = "hoat_dong", columnDefinition = "NVARCHAR(MAX)")
    private String hoatDong;

    @Column(name = "mieu_ta1", columnDefinition = "NVARCHAR(MAX)")
    private String mieuTa1;

    @Column(name = "mieu_ta2", columnDefinition = "NVARCHAR(MAX)")
    private String mieuTa2;

    @Column(name = "chuc_vu")
    private String chucVu;

    @Column(name = "hoc_ham")
    private String hocHam;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "so_giay_phep")
    private String soGiayPhep;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Column(name = "ngay_cap")
    private LocalDateTime ngayCap;

    @Column(name = "noi_cap")
    private String noiCap;

    @Column(name = "tep_dinh_kem")
    private String tepDinhKem;

    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = false;

    @OneToOne
    @JoinColumn(name = "ma_tai_khoan")
    private UAccount taiKhoan;

    @OneToOne
    @JoinColumn(name = "ma_phong_kham")
    private UClinic phongKham;

    @Column(name = "ngay_dang_ky")
    private LocalDateTime ngayDangKy;

    @OneToMany
    @JoinColumn(name = "ma_bac_si")
    private List<WorkExperience> workEx;

    @OneToMany
    @JoinColumn(name = "ma_bac_si")
    private List<TraningProgram> trainingProgram;
}