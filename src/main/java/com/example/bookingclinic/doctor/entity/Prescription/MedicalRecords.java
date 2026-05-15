package com.example.bookingclinic.doctor.entity.Prescription;

import java.time.LocalDateTime;

import com.example.bookingclinic.doctor.entity.ConfirmAppointment;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class MedicalRecords {

    @Id
    private String maHoSo;
    private String trieuChung;
    private String chuanDoan;
    private String ketLuan;
    private String ghiChu;
    private LocalDateTime ngayLap;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_lich_kham")
    private ConfirmAppointment confirmAppointment;
}

