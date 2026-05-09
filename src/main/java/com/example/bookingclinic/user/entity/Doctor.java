package com.example.bookingclinic.user.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bac_si") 
@Data
public class Doctor {
    @Id 
    @Column(length = 10)
    private String maBacSi;
    private Boolean gioiTinh;
    private String soDienThoai;
    private String email;
    private String queQuan;
    private String avt;
    @OneToOne
    @JoinColumn(name = "ma_chuyen_khoa")
    private Specialty specialty;
    private String bangCap;
    private String kinhNghiem;
    private String hoatDong;
    private String mieuTa1;
    private String mieuTa2;
    private String chucVu;
    private String hocHam;
    private String cccd;
    private String soGiapPhep;
    private LocalDateTime ngayCap;
    private String noiCap;
    @OneToOne @JoinColumn(name = "ma_tai_khoan")
    private Account taiKhoan;
    @ManyToOne @JoinColumn(name = "ma_phong_kham")
    private UClinic phongKham;


    @OneToMany
    @JoinColumn(name = "ma_bac_si")
    private List<WorkExperience> workEx;

    @OneToMany
    @JoinColumn(name = "ma_bac_si")
    private List<TraningProgram> trainingProgram;
}
