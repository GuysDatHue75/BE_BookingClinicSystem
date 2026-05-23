package com.example.bookingclinic.adminclinic.dto;

import java.time.LocalTime;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "khung_gio_kham")
public class TimeSlot {
    @Id
    private String maKhungGio;
    private String maCaLamViec;
    private LocalTime khungGioBatDau;
    private LocalTime khungGioKetThuc;
    @Builder.Default
    private Boolean isDeleted = false;

}
