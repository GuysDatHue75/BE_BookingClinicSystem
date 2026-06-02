package com.example.bookingclinic.adminclinic.dto.response.dashboard;

import java.math.BigDecimal;
 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
/**
 * KPI tổng quan phía trên dashboard:
 *  - Tổng doanh thu hôm nay + % thay đổi so hôm qua
 *  - Tổng bệnh nhân đã khám hôm nay + % thay đổi
 *  - Tổng lịch hẹn hôm nay + % thay đổi
 *  - Số bác sĩ đang hoạt động
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardKpiResponse {
 
    // ── Doanh thu ──────────────────────────────────────────────────────────────
    private BigDecimal doanhThuHomNay;
    private BigDecimal doanhThuHomQua;
    /** Phần trăm thay đổi so với hôm qua, ví dụ +2.14 hoặc -1.96 */
    private double phanTramDoanhThu;
 
    // ── Bệnh nhân ──────────────────────────────────────────────────────────────
    private long benhNhanHomNay;
    private long benhNhanHomQua;
    private double phanTramBenhNhan;
 
    // ── Lịch hẹn ───────────────────────────────────────────────────────────────
    private long lichHenHomNay;
    private long lichHenHomQua;
    private double phanTramLichHen;
 
    // ── Bác sĩ ─────────────────────────────────────────────────────────────────
    private long soLuongBacSiHoatDong;
}
