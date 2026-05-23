package com.example.bookingclinic.doctor.dto.schedule;

import lombok.Data;

@Data
public class UpdateSchedulesDTO {
    private String maLichLam; // Khóa chính lịch làm việc cần cập nhật
    private String trangThai; // Trạng thái mới muốn cập nhật
}