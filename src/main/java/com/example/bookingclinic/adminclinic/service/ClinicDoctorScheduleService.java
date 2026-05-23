package com.example.bookingclinic.adminclinic.service;

import java.time.LocalDate;
import java.util.List;

import com.example.bookingclinic.adminclinic.dto.response.DailyScheduleResponse;

public interface ClinicDoctorScheduleService {
    List<DailyScheduleResponse> getFormattedWeeklySchedule(String maPhongKham, String maBacSi, LocalDate targetDate);
}
