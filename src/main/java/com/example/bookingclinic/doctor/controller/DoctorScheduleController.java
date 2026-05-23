package com.example.bookingclinic.doctor.controller;

import com.example.bookingclinic.doctor.dto.schedule.GroupedScheduleDTO;
import com.example.bookingclinic.doctor.dto.schedule.SimpleDoctorScheduleDTO;
import com.example.bookingclinic.doctor.dto.schedule.UpdateSchedulesDTO;
import com.example.bookingclinic.doctor.dto.schedule.WeeklyScheduleRequestDTO;
import com.example.bookingclinic.doctor.entity.Schedule.DoctorSchedule;
import com.example.bookingclinic.doctor.service.DoctorScheduleService;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
            // 1. Tạo lịch bằng Service (trả về Entity)
            List<DoctorSchedule> createdSchedules = doctorScheduleService.createWeeklySchedule(requestDTO);

            // 2. Map Entity sang DTO ngay lập tức để tránh lỗi Lazy Initialization
            List<SimpleDoctorScheduleDTO> responseDTOs = createdSchedules.stream().map(schedule -> {
                SimpleDoctorScheduleDTO dto = new SimpleDoctorScheduleDTO();
                dto.setMaLichLam(schedule.getMaLichLam());
                dto.setNgayLamViec(schedule.getNgayLamViec());
                if (schedule.getKhungGioKham() != null) {
                    dto.setKhungGio(schedule.getKhungGioKham().getKhungGioBatDau() + " - "
                            + schedule.getKhungGioKham().getKhungGioKetThuc());
                }
                dto.setLoaiHinhKham(schedule.getLoaiHinhKham());
                dto.setTrangThai(schedule.getTrangThai());
                return dto;
            }).collect(Collectors.toList());

            // 3. Trả về DTO thay vì Entity
            return ResponseEntity.ok(responseDTOs);
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
            // 1. Cập nhật và lấy ra danh sách Entity từ Service
            List<DoctorSchedule> updatedSchedules = doctorScheduleService.updateSchedules(requesList);

            // 2. MAP SANG DTO để tránh lỗi Hibernate Proxy
            List<SimpleDoctorScheduleDTO> responseDTOs = updatedSchedules.stream().map(schedule -> {
                SimpleDoctorScheduleDTO dto = new SimpleDoctorScheduleDTO();
                dto.setMaLichLam(schedule.getMaLichLam());
                dto.setNgayLamViec(schedule.getNgayLamViec());

                if (schedule.getKhungGioKham() != null) {
                    String gioHienThi = schedule.getKhungGioKham().getKhungGioBatDau() + " - " +
                            schedule.getKhungGioKham().getKhungGioKetThuc();
                    dto.setKhungGio(gioHienThi);
                }

                dto.setLoaiHinhKham(schedule.getLoaiHinhKham());
                dto.setTrangThai(schedule.getTrangThai());
                return dto;
            }).collect(Collectors.toList());

            // 3. Trả về mảng DTO sạch sẽ
            return ResponseEntity.ok(responseDTOs);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}