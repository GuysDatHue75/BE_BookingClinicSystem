package com.example.bookingclinic.adminclinic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminclinic.dto.response.DailyScheduleResponse;
import com.example.bookingclinic.adminclinic.service.DoctorScheduleService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/adminclinic/appointment")
@RequiredArgsConstructor
public class DoctorScheduleController {

    private final DoctorScheduleService doctorScheduleService;

    @GetMapping("/{maPhongKham}/{maBacSi}/weeklySchedule")
    public ResponseEntity<List<DailyScheduleResponse>> getDoctorWeeklySchedule(
            @PathVariable String maPhongKham,
            @PathVariable String maBacSi,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate) {
        List<DailyScheduleResponse> result = doctorScheduleService.getFormattedWeeklySchedule(maPhongKham, maBacSi, targetDate);
        return ResponseEntity.ok(result);
    }   
}
