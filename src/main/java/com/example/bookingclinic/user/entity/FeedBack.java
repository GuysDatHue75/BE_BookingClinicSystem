package com.example.bookingclinic.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "danh_gia")
public class FeedBack {
    @Id
    private String maDanhGia;
    @ManyToOne
    @JoinColumn(name = "ma_benh_nhan")
    private Patient benhNhan;
    private String maBacSi;
    private String maPhongKham;
    private int soSaoPhongKham;
    private int soSaoBacSi;
    private String noiDung;
    private String maLichKham;
    private LocalDateTime ngayDanhGia;
}
