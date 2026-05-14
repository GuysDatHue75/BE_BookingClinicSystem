package com.example.bookingclinic.adminclinic.dto.response;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyScheduleResponse {
    private LocalDate ngay;
    private String thu;
    private List<SlotItemDTO> caSang;
    private List<SlotItemDTO> caChieu;  
}
