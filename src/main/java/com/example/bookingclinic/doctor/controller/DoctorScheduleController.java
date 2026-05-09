package com.example.bookingclinic.doctor.controller;

import com.example.bookingclinic.doctor.dto.schedule.GroupedScheduleDTO;
import com.example.bookingclinic.doctor.dto.schedule.UpdateSchedulesDTO;
import com.example.bookingclinic.doctor.dto.schedule.WeeklyScheduleRequestDTO;
import com.example.bookingclinic.doctor.entity.DoctorSchedule;
import com.example.bookingclinic.doctor.service.DoctorScheduleService;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor-schedules")
@RequiredArgsConstructor
public class DoctorScheduleController {

    private final DoctorScheduleService doctorScheduleService;

    // 1. lập lịch làm việc
    // http://localhost:8080/api/v1/doctor-schedules/create-weekly
    @PostMapping("/create-weekly")
    public ResponseEntity<?> createWeeklySchedule(@RequestBody WeeklyScheduleRequestDTO requestDTO) {
        try {
            // Gọi Service để xử lý luồng 2 vòng For
            List<DoctorSchedule> createdSchedules = doctorScheduleService.createWeeklySchedule(requestDTO);
            return ResponseEntity.ok(createdSchedules);
        } catch (RuntimeException e) {
            // Bắt lỗi nếu bị trùng toàn bộ lịch (throw từ Service)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. Lấy danh sách lịch làm việc
    // http://localhost:8080/api/v1/doctor-schedules/list?maBacSi=BS01&startDate=2026-06-01&endDate=2026-06-07
    @GetMapping("/list")
    public ResponseEntity<List<GroupedScheduleDTO>> getSchedules(
            @RequestParam String maBacSi,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<GroupedScheduleDTO> result = doctorScheduleService.getGroupedWeeklySchedules(maBacSi, startDate, endDate);

        return ResponseEntity.ok(result);
    }

    // 3. update lịch làm việc
    // http://localhost:8080/api/v1/doctor-schedules/update
    @PutMapping("/update")
    public ResponseEntity<?> updateSchedules(@RequestBody List<UpdateSchedulesDTO> requesList) {
        try {
            List<DoctorSchedule> result = doctorScheduleService.updateSchedules(requesList);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}