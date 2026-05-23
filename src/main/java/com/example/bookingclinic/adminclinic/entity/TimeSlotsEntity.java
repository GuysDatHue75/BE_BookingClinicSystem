package com.example.bookingclinic.adminclinic.entity;

import java.time.LocalTime;

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
@Table(name = "khung_gio_kham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSlotsEntity {
    @Id
    @Column(name = "ma_khung_gio", length = 10, nullable = false)
    private String maKhungGio;

    @ManyToOne @JoinColumn(name = "ma_ca_lam_viec")
    private ShiftsEntity shift;

    @Column(name = "khung_gio_bat_dau", nullable = false)
    private LocalTime khungGioBatDau;

    @Column(name = "khung_gio_ket_thuc", nullable = false)
    private LocalTime khungGioKetThuc;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}
