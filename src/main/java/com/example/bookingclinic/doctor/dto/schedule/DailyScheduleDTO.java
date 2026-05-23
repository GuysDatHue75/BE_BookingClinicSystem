package com.example.bookingclinic.doctor.dto.schedule;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class DailyScheduleDTO {
    private LocalDate ngayLamViec;

    private List<String> khungGio;
    private String loaiHinhKham; 
    private String trangThai; // VD: "Trống", "Đã khóa"

}