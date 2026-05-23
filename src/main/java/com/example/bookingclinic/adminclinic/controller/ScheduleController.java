package com.example.bookingclinic.adminclinic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminclinic.dto.request.DoctorSchedulesRequest;
import com.example.bookingclinic.adminclinic.service.ScheduleService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/v1/adminclinic/{maPhongKham}/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    //lấy lịch tuần có phân trang
    @GetMapping("/weekly")
    public ResponseEntity<?> getWeeklySchedule(@PathVariable String maPhongKham, @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(scheduleService.getWeeklySchedule(maPhongKham, page));
    }

    //Tạo và Cập nhật lịch làm việc
    @PutMapping("/shifts")
    public ResponseEntity<?> updateSchedule(@PathVariable String maPhongKham, @RequestBody DoctorSchedulesRequest request) {
        scheduleService.updateSchedule(maPhongKham, request);
        return ResponseEntity.ok("cập nhật phân công lịch làm việc thành công!");
    }
    
    
    
}
