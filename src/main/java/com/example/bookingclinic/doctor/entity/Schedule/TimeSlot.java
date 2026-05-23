package com.example.bookingclinic.doctor.entity.Schedule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Entity
@Table(name = "khung_gio_kham")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {

    @Id
    @Column(name = "ma_khung_gio", length = 10)
    private String maKhungGio;

    @Column(name = "khung_gio_bat_dau", nullable = false)
    private LocalTime khungGioBatDau;

    @Column(name = "khung_gio_ket_thuc", nullable = false)
    private LocalTime khungGioKetThuc;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    // Khóa ngoại trỏ về bảng ca_lam_viec
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_ca_lam_viec", nullable = false)
    private WorkShift caLamViec;
}