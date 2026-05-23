package com.example.bookingclinic.adminclinic.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lich_kham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentScheduleEntity {
    @Id
    @Column(name = "ma_lich_kham", length = 255, nullable = false)
    private String maLichKham;

    @ManyToOne @JoinColumn(name = "ma_benh_nhan")
    private PatientsEntity patient;

    @ManyToOne @JoinColumn(name = "ma_bac_si")
    private DoctorEntity doctor;

    @Column(name = "ngay_kham", nullable = false)
    private LocalDate ngayKham;

    @Column(name = "loai_kham", length = 100, nullable = false)
    private String loaiKham;

    @Column(name = "trang_thai", length = 50)
    private String trangThai;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDateTime ngayTao;

    @Column(name = "danh_gia")
    private Integer danhGia;

    @Column(name = "da_gui_thong_bao")
    private Integer daGuiThongBao;

    @ManyToOne @JoinColumn(name = "ma_lich_lam")
    private DoctorScheduleEntity doctorSchedule;

    @Lob
    @Column(name = "ly_do_kham")
    private String lyDoKham;

    @Column(name = "gio_kham")
    private LocalTime gioKham;
}
