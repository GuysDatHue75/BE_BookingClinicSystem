package com.example.bookingclinic.doctor.dto.ConfirmAppointment;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {
    private String maLichKham;

    // --- Lấy từ bảng Account ---
    private String hoVaTen;
    private LocalDate ngaySinh; // Hoặc String tùy kiểu DB của bạn
    private Boolean gioiTinh;
    private String soDienThoai;
    private String diaChi;

    // --- Lấy từ bảng Lịch Khám ---
    private String lyDoKham;
    private String trangThai;

    // --- Lấy từ bảng Lịch Làm Việc ---
    private LocalDate ngayLamViec; // Ngày khám
    private String khungGio; // Giờ khám
}
