package com.example.bookingclinic.adminclinic.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
// DTO cho danh sách Lịch hẹn gần đây (Table)
public class RecentAppointmentDTO {
    private String patientName; 
    private String date; // "20-07-28"
    private String time; // "09:00 AM"
    private String doctorName;
    private String treatment;
    private String status;
}
