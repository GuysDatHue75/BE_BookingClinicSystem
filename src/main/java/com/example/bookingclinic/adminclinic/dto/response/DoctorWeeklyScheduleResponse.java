package com.example.bookingclinic.adminclinic.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorWeeklyScheduleResponse {
    private String maLichLamViec;
    private LocalDate ngayLamViec;
    private LocalTime gioBatDau;
    private String trangThaiLichLamViec;

    private String maLichKham;
    private String tenBenhNhan;
    private String trangThaiLichKham;
}
