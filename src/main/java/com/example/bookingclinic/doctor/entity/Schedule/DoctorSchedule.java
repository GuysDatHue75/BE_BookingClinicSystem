package com.example.bookingclinic.doctor.entity.Schedule;

import java.time.LocalDate;

import com.example.bookingclinic.doctor.entity.Doctor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "lich_lam_viec")
@Data
public class DoctorSchedule {

    @Id
    @Column(name = "ma_lich_lam", length = 255)
    private String maLichLam;

    @Column(name = "ngay_lam_viec")
    private LocalDate ngayLamViec;

    @Column(name = "loai_hinh_kham", length = 255)
    private String loaiHinhKham;

    @Column(name = "trang_thai", length = 50)
    private String trangThai;

    @Column(name = "ma_phong_kham", length = 255)
    private String maPhongKham;

    // --- KHÓA NGOẠI ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_bac_si", nullable = false)
    @JsonIgnore
    private Doctor bacSi;

    // Đã thay thế String khungGio bằng Object TimeSlot
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "ma_khung_gio")
    private TimeSlot khungGioKham;
}