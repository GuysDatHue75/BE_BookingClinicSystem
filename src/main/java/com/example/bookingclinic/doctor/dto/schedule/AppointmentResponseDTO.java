package com.example.bookingclinic.doctor.dto.schedule;

import lombok.Data;

@Data
public class AppointmentResponseDTO {
    private String maLichKham;
    private String tenPhongKham;
    private String tenBenhNhan;
    private String sdtBenhNhan;
    private String tenBacSi;

}