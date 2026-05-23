package com.example.bookingclinic.adminclinic.dto.response;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SlotItemDTO{
        private String maLichLamViec;
        private LocalTime thoiGian;
        private boolean daDat;
        private String tenBenhNhan;    
}
