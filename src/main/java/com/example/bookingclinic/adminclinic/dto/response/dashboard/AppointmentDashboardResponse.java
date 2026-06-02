package com.example.bookingclinic.adminclinic.dto.response.dashboard;

import java.time.LocalDate;
import java.time.LocalTime;
 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
/**
 * Thông tin 1 lịch hẹn hiển thị trong bảng dashboard.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDashboardResponse {
 
    private String maLichKham;
 
    // ── Bệnh nhân ──────────────────────────────────────────────────────────────
    private String maBenhNhan;
    private String tenBenhNhan;      // account.hoVaTen
 
    // ── Bác sĩ ─────────────────────────────────────────────────────────────────
    private String maBacSi;
    private String tenBacSi;
 
    // ── Lịch ───────────────────────────────────────────────────────────────────
    private LocalDate ngayKham;
    private LocalTime gioKham;
    private String loaiKham;
 
    /**
     * Trạng thái gốc từ DB: DaXacNhan | ChoXacNhan | DaHuy | DaKham
     * Frontend tự map sang label + màu tương ứng.
     */
    private String trangThai;
 
    /** Điểm đánh giá 1–5, null nếu chưa có */
    private Integer danhGia;
}
