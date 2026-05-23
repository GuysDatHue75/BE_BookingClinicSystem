package com.example.bookingclinic.adminclinic.entity;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ca_lam_viec")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftsEntity {
    @Id
    @Column(name = "ma_ca_lam_viec", length = 10, nullable = false)
    private String maCaLamViec;

    @Column(name = "ten_ca_lam_viec", length = 255, nullable = false)
    private String tenCaLamViec;

    @Column(name = "gio_bat_dau", nullable = false)
    private LocalTime gioBatDau;

    @Column(name = "gio_ket_thuc", nullable = false)
    private LocalTime gioKetThuc;
}
