package com.example.bookingclinic.doctor.entity.Schedule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Entity
@Table(name = "ca_lam_viec")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkShift {

    @Id
    @Column(name = "ma_ca_lam_viec", length = 10)
    private String maCaLamViec;

    @Column(name = "ten_ca_lam_viec", length = 255, nullable = false)
    private String tenCaLamViec;

    @Column(name = "gio_bat_dau", nullable = false)
    private LocalTime gioBatDau;

    @Column(name = "gio_ket_thuc", nullable = false)
    private LocalTime gioKetThuc;
}