package com.example.bookingclinic.doctor.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "tu_van")
@Data
public class Advise {

    @Id
    @Column(name = "ma_tu_van", length = 50)
    private String maTuVan;

    @Column(name = "ma_benh_nhan", nullable = false)
    private String maBenhNhan;

    @Column(name = "ma_bac_si", nullable = false)
    private String maBacSi;

    @Column(name = "ma_phong_kham", nullable = false)
    private String maPhongKham;

    @Column(name = "cau_hoi")
    private String cauHoi;

    @Column(name = "cau_tra_loi")
    private String cauTraLoi;

    @Column(name = "thoi_gian_hoi")
    private LocalDateTime thoiGianHoi;

    @Column(name = "thoi_gian_tra_loi")
    private LocalDateTime thoiGianTraLoi;

    @Column(name = "trang_thai_tra_loi")
    private Boolean trangThai = false;
}