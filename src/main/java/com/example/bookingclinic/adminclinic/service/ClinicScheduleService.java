package com.example.bookingclinic.adminclinic.service;

import com.example.bookingclinic.adminclinic.dto.request.DoctorSchedulesRequest;
import com.example.bookingclinic.adminclinic.dto.response.WeeklyScheduleResponse;

public interface ClinicScheduleService {
    WeeklyScheduleResponse getWeeklySchedule(String maPhongKham, int page);
    void updateSchedule(String maLichLamViec, DoctorSchedulesRequest request);
}
