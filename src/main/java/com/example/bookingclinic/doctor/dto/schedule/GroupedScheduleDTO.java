package com.example.bookingclinic.doctor.dto.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupedScheduleDTO {
    private String thuTrongTuan; // Chứa chữ "Thứ 2", "Thứ 3"..., "Chủ Nhật"
    private List<SimpleDoctorScheduleDTO> danhSachLich; // Danh sách lịch tương ứng của ngày đó

}