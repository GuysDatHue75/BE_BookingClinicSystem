package com.example.bookingclinic.adminclinic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminclinic.dto.request.DoctorSchedulesRequest;
import com.example.bookingclinic.adminclinic.dto.request.WeeklyScheduleUpdateRequest;
import com.example.bookingclinic.adminclinic.repository.ClinicWorkShiftRepository;
import com.example.bookingclinic.adminclinic.service.ClinicScheduleService;

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
public class ClinicScheduleController {
    private final ClinicScheduleService scheduleService;
    private final ClinicWorkShiftRepository workShiftRepository;

    //lấy lịch tuần có phân trang
    @GetMapping("/weekly")
    public ResponseEntity<?> getWeeklySchedule(@PathVariable String maPhongKham, @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(scheduleService.getWeeklySchedule(maPhongKham, page));
    }

    //Tạo và Cập nhật lịch làm việc
    @PutMapping("/shifts")
    public ResponseEntity<?> updateSchedule(@PathVariable String maPhongKham, @RequestBody WeeklyScheduleUpdateRequest request) {
        scheduleService.updateSchedule(maPhongKham, request);
        return ResponseEntity.ok("Cập nhật phân công lịch làm việc thành công!");
    }
    
    @GetMapping("/next-available-week")
    public ResponseEntity<?> getNextAvailableWeek(@PathVariable String maPhongKham) {
        return ResponseEntity.ok(scheduleService.getNextAvailableWeek(maPhongKham));
    }
    
    @GetMapping("/work-shifts")
    public ResponseEntity<?> getAllWorkShifts() {
        return ResponseEntity.ok(workShiftRepository.findAll());
    }
}
