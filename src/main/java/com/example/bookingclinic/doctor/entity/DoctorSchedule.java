package com.example.bookingclinic.doctor.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "lich_lam_viec")
@Data
public class DoctorSchedule {
    @Id
    private String maLichLam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_bac_si")
    @JsonIgnore
    private Doctor bacSi;

    private LocalDate ngayLamViec;
    private String khungGio;
    private String loaiHinhKham;
    private String trangThai;
}



