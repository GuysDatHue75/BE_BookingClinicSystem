package com.example.bookingclinic.doctor.dto.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyScheduleRequestDTO {
    private String maBacSi;
    private List<DailyScheduleDTO> danhSachNgayLamViec;
}