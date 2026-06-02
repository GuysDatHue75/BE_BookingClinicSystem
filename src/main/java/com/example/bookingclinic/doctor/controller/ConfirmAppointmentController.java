package com.example.bookingclinic.doctor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.doctor.dto.MessageResponse;
import com.example.bookingclinic.doctor.dto.schedule.ScheduleResponseDTO;
import com.example.bookingclinic.doctor.entity.Schedule.Appointment;
import com.example.bookingclinic.doctor.service.ConfirmAppointmentService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/confirm-appointment")
@RequiredArgsConstructor
public class ConfirmAppointmentController {
    private final ConfirmAppointmentService confirmAppointmentService;

    // 1 Xem danh sách chờ duyệt
    // http://localhost:8080/api/v1/confirm-appointment/pending?maBacSi=BS01
    @GetMapping("/pending")
    public ResponseEntity<List<ScheduleResponseDTO>> getPending(@RequestParam String maBacSi) {

        return ResponseEntity.ok(confirmAppointmentService.layDSChoDuyet(maBacSi));
    }

    // 2. Duyệt/Hủy lịch khám
    @PutMapping("/approve")
    public ResponseEntity<?> approve(@RequestBody ScheduleResponseDTO requestDTO) {
        try {
            String result = confirmAppointmentService.approveAppointment(requestDTO);
            return ResponseEntity.ok(new MessageResponse(result));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 3. Get DaXacNhan
    // Đường dẫn mẫu: http://localhost:8080/api/v1/confirm-appointment/doctor-waiting?maBacSi=BS001
    @GetMapping("/doctor-waiting")
    public ResponseEntity<?> getDoctorWaitingList(@RequestParam("maBacSi") String maBacSi) {

        return ResponseEntity.ok(confirmAppointmentService.layDanhsachDaXacNhan(maBacSi));

    }

}
