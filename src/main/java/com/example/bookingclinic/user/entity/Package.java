package com.example.bookingclinic.user.entity;

import java.time.LocalDate;
import java.util.List;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "goi_dang_ky")
public class Package {
    @Id
    private String maGoi;
    private String tenGoi;
    private double gia;
    private int thoiHanNgay;
    private String moTa;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private Integer trangThai;
    @ElementCollection
    @CollectionTable(name = "tinh_nang_goi_dang_ky", joinColumns = @JoinColumn(name = "ma_goi"))
    @Column(name = "tinh_nang")
    private List<String> tinhNangs;
}
