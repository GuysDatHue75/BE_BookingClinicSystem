package com.example.bookingclinic.doctor.entity;

import java.time.LocalDate;

import com.example.bookingclinic.adminclinic.dto.TimeSlot;
import com.example.bookingclinic.user.entity.Doctor;
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
    @JoinColumn(name = "ma_khung_gio")
    @ManyToOne
    private TimeSlot timeSlot;
    private String loaiHinhKham;
    private String trangThai;
    private String maPhongKham;
}



