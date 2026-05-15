package com.example.bookingclinic.doctor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.doctor.dto.ConfirmAppointment.AppointmentRequestDTO;
import com.example.bookingclinic.doctor.dto.ConfirmAppointment.AppointmentResponseDTO;
import com.example.bookingclinic.doctor.entity.ConfirmAppointment;
import com.example.bookingclinic.doctor.entity.DoctorSchedule;
import com.example.bookingclinic.doctor.repository.ConfirmAppointmentRepository;
import com.example.bookingclinic.doctor.repository.DoctorScheduleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfirmAppointmentService {
    private final ConfirmAppointmentRepository confirmAppointmentRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;

    // Lấy danh sách
    public List<AppointmentResponseDTO> layDSChoDuyet(String maBacSi) {
        return confirmAppointmentRepository.getDSChuaXacNhan(maBacSi);
    }

    // Xử lý duyệt
    @Transactional
    public String approveAppointment(AppointmentRequestDTO requestDTO) {
        // 1. tìm lịch khám
        ConfirmAppointment appointment = confirmAppointmentRepository.findById(requestDTO.getMaLichKham()).orElseThrow(
                () -> new RuntimeException("Lỗi : Không tìm thấy lịch khám với mã:" + requestDTO.getMaLichKham()));

        // 2. duyệt trạng thái
        appointment.setTrangThai(requestDTO.getTrangThai());

        // 3. "hủy"
        if ("Duyet".equalsIgnoreCase(requestDTO.getTrangThai())) {
            DoctorSchedule schedule = appointment.getLichLamViec();
            if (schedule != null) {
                schedule.setTrangThai("DaDat");
                doctorScheduleRepository.save(schedule);
            }
        } else if ("Huy".equalsIgnoreCase(requestDTO.getTrangThai())) {
            DoctorSchedule schedule = appointment.getLichLamViec();
            if (schedule != null) {
                schedule.setTrangThai("HoatDong");
                doctorScheduleRepository.save(schedule);
            }
        }
        confirmAppointmentRepository.save(appointment);
        return " Xữ lý lịch khám " + requestDTO.getMaLichKham() + " thành công với trạng thái : "
                + requestDTO.getTrangThai();
    }

}
