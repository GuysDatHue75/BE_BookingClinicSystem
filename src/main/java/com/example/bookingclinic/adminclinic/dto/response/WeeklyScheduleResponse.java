package com.example.bookingclinic.adminclinic.dto.response;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeeklyScheduleResponse {
    private LocalDate ngayDauTuan;
    private LocalDate ngayCuoiTuan;
    private List<DailyScheduleDto> dailySchedules;

    @Data
    @Builder
    public static class DailyScheduleDto{
        private LocalDate ngay;
        private List<ShiftScheduleDto> shifts;
    }

    @Data
    @Builder
    public static class ShiftScheduleDto{
        private String maCaLamViec;
        private String tenCaLamViec;
        private List<DoctorBasicDto> doctors;
    }

    @Data
    @Builder
    public static class DoctorBasicDto{
        private String maBacSi;
        private String tenBacSi;
    }
}
