package com.example.bookingclinic.doctor.entity.Prescription;

import java.time.LocalDateTime;

import com.example.bookingclinic.doctor.entity.Schedule.Appointment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ho_so_kham")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecords {

    @Id
    @Column(name = "ma_ho_so", length = 10)
    private String maHoSo;

    @Column(name = "trieu_chung", columnDefinition = "NVARCHAR(MAX)")
    private String trieuChung;

    @Column(name = "chuan_doan", columnDefinition = "NVARCHAR(MAX)")
    private String chuanDoan;

    @Column(name = "ket_luan", columnDefinition = "NVARCHAR(MAX)")
    private String ketLuan;

    @Column(name = "ghi_chu", columnDefinition = "NVARCHAR(MAX)")
    private String ghiChu;

    @Column(name = "ngay_lap")
    private LocalDateTime ngayLap;

    @OneToOne
    @JoinColumn(name = "ma_lich_kham")
    private Appointment appointment;
}