package com.example.bookingclinic.adminclinic.service;

import com.example.bookingclinic.adminclinic.dto.request.WeeklyScheduleUpdateRequest;
import com.example.bookingclinic.adminclinic.dto.response.NextWeekAvailableResponse;
import com.example.bookingclinic.adminclinic.dto.response.WeeklyScheduleResponse;

public interface ClinicScheduleService {
    WeeklyScheduleResponse getWeeklySchedule(String maPhongKham, int page);
    void updateSchedule(String maPhongKham, WeeklyScheduleUpdateRequest request);
    NextWeekAvailableResponse getNextAvailableWeek(String maPhongKham);
}
