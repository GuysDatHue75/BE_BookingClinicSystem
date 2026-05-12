package com.example.bookingclinic.doctor.dto.schedule;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ScheduleResponseDTO {
    private String maLichLam;
    private LocalDate ngayLamViec;
    private String khungGio;
    private String loaiHinhKham;
    private String trangThai;
}