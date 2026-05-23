package com.example.bookingclinic.adminclinic.dto.response;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableSlotResponse {
    private String maLichLamViec;
    private LocalTime thoiGianBatDau;
    private LocalTime thoiGianKetThuc;
}
