package com.example.bookingclinic.doctor.dto.schedule;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class DailyScheduleDTO {
    private LocalDate ngayLamViec;
    private List<String> khungGio; // VD: ["08:00 - 09:00", "09:00 - 10:00"]
    private String loaiHinhKham;
    private String trangThai;
}