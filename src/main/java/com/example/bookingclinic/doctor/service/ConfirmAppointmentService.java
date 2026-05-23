package com.example.bookingclinic.doctor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.doctor.dto.schedule.ScheduleResponseDTO;
import com.example.bookingclinic.doctor.entity.Schedule.Appointment;
import com.example.bookingclinic.doctor.entity.Schedule.DoctorSchedule;
import com.example.bookingclinic.doctor.repository.ScheduleRepository.AppointmentRepository;
import com.example.bookingclinic.doctor.repository.ScheduleRepository.DoctorScheduleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfirmAppointmentService {

    // Biến instance viết thường chữ cái đầu
    private final AppointmentRepository appointmentRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;

    // Lấy danh sách chưa xác nhận
    public List<ScheduleResponseDTO> layDSChoDuyet(String maBacSi) {
        return appointmentRepository.getDSChuaXacNhan(maBacSi);
    }

    // Xử lý duyệt lịch khám
    @Transactional
    public String approveAppointment(ScheduleResponseDTO requestDTO) {

        // 🛠️ SỬA LỖI TẠI ĐÂY: Thay "AppointmentRepository.findById" thành
        // "appointmentRepository.findById" (dùng biến instance)
        // Đồng thời sửa requestDTO.getMaLichLam() thành requestDTO.getMaLichKham() để
        // tìm đúng Id của lịch khám
        Appointment appointment = appointmentRepository.findById(requestDTO.getMaLichKham())
                .orElseThrow(() -> new RuntimeException(
                        "Lỗi: Không tìm thấy lịch khám với mã: " + requestDTO.getMaLichKham()));

        // 2. Cập nhật trạng thái của cuộc hẹn (Appointment)
        appointment.setTrangThai(requestDTO.getTrangThai());

        // 3. Đồng bộ trạng thái sang Lịch làm việc tổng của Bác sĩ (DoctorSchedule)
        DoctorSchedule schedule = appointment.getLichLamViec();
        if (schedule != null) {
            if ("DaXacNhan".equalsIgnoreCase(requestDTO.getTrangThai())) {
                schedule.setTrangThai("DaDat");
                doctorScheduleRepository.save(schedule);
            } else if ("DaHuy".equalsIgnoreCase(requestDTO.getTrangThai())) {
                schedule.setTrangThai("HoatDong");
                doctorScheduleRepository.save(schedule);
            }
        }

        // Lưu lại thông tin cuộc hẹn
        appointmentRepository.save(appointment);

        return "Xử lý lịch khám " + requestDTO.getMaLichKham() + " thành công với trạng thái: "
                + requestDTO.getTrangThai();
    }
}