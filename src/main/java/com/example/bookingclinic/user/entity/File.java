package com.example.bookingclinic.user.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ho_so_kham")
@Data
public class File {
    @Id 
    @Column(length = 10)
    private String maHoSo;
    @OneToOne 
    @JoinColumn(name = "ma_lich_kham")
    private Calendar calendar;
    private String trieuChung;
    private String chuanDoan;
    private String ketLuan;
    private String ghiChu;
    private LocalDateTime ngayLap;
    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FileImage> ListImage;
}
