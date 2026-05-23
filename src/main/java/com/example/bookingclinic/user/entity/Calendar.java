package com.example.bookingclinic.user.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.bookingclinic.adminclinic.entity.DoctorScheduleEntity;

import jakarta.persistence.Column;
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
    @Column(name = "ma_lich_kham", length = 255)
    private String maLichKham;

    @ManyToOne
    @JoinColumn(name = "ma_benh_nhan")
    private UPatient patient;

    @ManyToOne
    @JoinColumn(name = "ma_bac_si")
    private UDoctor bacSi;

    @Column(name = "gio_kham")
    private LocalTime gioKham;

    @Column(name = "ngay_kham")
    private LocalDate ngayKham;

    @Column(name = "loai_kham")
    private String loaiKham;

    @Column(name = "trang_thai")
    private String trangThai;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "danh_gia")
    private Integer danhGia;

    @Column(name = "da_gui_thong_bao")
    private Integer daGuiThongBao = 0;

    @OneToOne
    @JoinColumn(name = "ma_lich_lam")
    private DoctorScheduleEntity doctorSchedule;

    @Column(name = "ly_do_kham")
    private String lyDoKham;
}