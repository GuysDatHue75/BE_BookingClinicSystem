package com.example.bookingclinic.user.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.bookingclinic.doctor.entity.DoctorSchedule;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "lich_kham")
@NoArgsConstructor
@AllArgsConstructor
public class Calendar {
    @Id
    private String maLichKham;
    @ManyToOne
    @JoinColumn(name = "ma_benh_nhan")
    private Patient patient;
    @ManyToOne 
    @JoinColumn(name = "ma_bac_si")
    private Doctor bacSi;
    private LocalTime gioKham;
    private LocalDate ngayKham;
    private String loaiKham;
    private String trangThai;
    private LocalDateTime ngayTao;
    private Integer danhGia;
    private Integer daGuiThongBao = 0;
    @OneToOne
    @JoinColumn(name = "ma_lich_lam")
    private DoctorSchedule doctorSchedule;
    private String lyDoKham;

}