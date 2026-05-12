package com.example.bookingclinic.doctor.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "bac_si")
@Data
public class Doctor {
    @Id
    @Column(name = "ma_bac_si", length = 10)
    private String maBacSi;
    private String chuyenKhoa;
    private String bangCap;
    private String kinhNghiem;
    private String hoatDong;
    private String mieuTa;
    private String chucVu;
    private String hocHam;
    private String soGiapPhep;
    private LocalDateTime ngayCap;
    private String noiCap;
    private String maPhongKham;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_tai_khoan")
    private Account taiKhoan;
}