package com.example.bookingclinic.user.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "lich_kham")
public class Calendar {
    @Id
    @Column(length = 10)
    private String maLichKham;
    @ManyToOne
    @JoinColumn(name = "ma_benh_nhan")
    private Patient patient;
    @ManyToOne 
    @JoinColumn(name = "ma_bac_si")
    private Doctor bacSi;
    private LocalDate ngayKham;
    private LocalTime gioKham;
    private String loaiKham;
    private String trangThai;
    private LocalDateTime ngayTao;
    private int danhGia;
}