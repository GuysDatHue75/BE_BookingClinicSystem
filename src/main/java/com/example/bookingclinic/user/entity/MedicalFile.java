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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ho_so_kham")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalFile {

    @Id
    @Column(name = "ma_ho_so", length = 10)
    private String maHoSo;

    @OneToOne
    @JoinColumn(name = "ma_lich_kham")
    private Calendar calendar;

    @Column(name = "trieu_chung")
    private String trieuChung;

    @Column(name = "chuan_doan")
    private String chuanDoan;

    @Column(name = "ket_luan")
    private String ketLuan;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "ngay_lap")
    private LocalDateTime ngayLap;

    @OneToMany(
        mappedBy = "file",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    private List<FileImage> listImage;
}