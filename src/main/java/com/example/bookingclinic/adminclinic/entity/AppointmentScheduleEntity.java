package com.example.bookingclinic.adminclinic.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @ManyToOne @JoinColumn(name = "ma_phong_kham")
    private ClinicEntity clinic;

    @Column(name = "ngay_kham", nullable = false)
    private LocalDate ngayKham;

    @Column(name = "loai_kham", length = 100)
    private String loaiKham;

    @Column(name = "trang_thai", length = 50)
    private String trangThai;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDateTime ngayTao;

    @ManyToOne @JoinColumn(name = "ma_lich_lam_viec")
    private DoctorScheduleEntity doctorSchedule;
}
