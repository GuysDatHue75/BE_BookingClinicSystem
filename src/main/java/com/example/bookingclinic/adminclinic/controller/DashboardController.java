package com.example.bookingclinic.adminclinic.controller;
 
import java.time.LocalDate;
import java.util.List;
 
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminclinic.dto.response.DashboardResponseDTO;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.AgeGroupChartResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.AppointmentDashboardResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.DashboardKpiResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.HeatmapResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.RecentReviewResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.RevenueChartResponse;
import com.example.bookingclinic.adminclinic.entity.ClinicEntity;
import com.example.bookingclinic.adminclinic.repository.ClinicRepository;
import com.example.bookingclinic.adminclinic.service.DashboardService;
 
import lombok.RequiredArgsConstructor;
 
/**
 * Dashboard controller cho role PhongKham.
 *
 * Base URL: /api/clinic/dashboard
 *
 * maPhongKham được lấy tự động từ JWT (account đang đăng nhập)
 * thông qua helper resolveClinicId() – không cần truyền trên URL.
 *
 * Endpoints:
 *  GET /              → Full dashboard (load lần đầu)
 *  GET /kpi           → 4 KPI cards
 *  GET /revenue       → Biểu đồ doanh thu   (?mode=WEEK|MONTH|YEAR)
 *  GET /age-chart     → Biểu đồ độ tuổi     (?days=7|30|90)
 *  GET /heatmap       → Ma trận giờ cao điểm (?days=30)
 *  GET /appointments  → Lịch hẹn gần nhất   (?limit=10)
 *  GET /appointments/by-date → Lịch hẹn theo ngày (?date=2025-07-20)
 *  GET /review/recent → Đánh giá mới nhất
 */
@RestController
@RequestMapping("/api/clinic/dashboard")
@RequiredArgsConstructor
public class DashboardController {
 
    private final DashboardService  dashboardService;
    private final ClinicRepository  clinicRepository;
 
    // ── Full dashboard ────────────────────────────────────────────────────────
 
    /**
     * Load toàn bộ dữ liệu dashboard trong 1 request.
     * Frontend dùng khi khởi tạo trang; sau đó gọi riêng từng endpoint khi filter thay đổi.
     */
    @GetMapping
    public ResponseEntity<DashboardResponseDTO> getFullDashboard(Authentication auth) {
        String clinicId = resolveClinicId(auth);
        return ResponseEntity.ok(dashboardService.getFullDashboard(clinicId));
    }
 
    // ── KPI ──────────────────────────────────────────────────────────────────
 
    @GetMapping("/kpi")
    public ResponseEntity<DashboardKpiResponse> getKpi(Authentication auth) {
        return ResponseEntity.ok(dashboardService.getKpi(resolveClinicId(auth)));
    }
 
    // ── Biểu đồ doanh thu ────────────────────────────────────────────────────
 
    /**
     * @param mode WEEK | MONTH | YEAR (mặc định WEEK)
     */
    @GetMapping("/revenue")
    public ResponseEntity<RevenueChartResponse> getRevenue(
            Authentication auth,
            @RequestParam(defaultValue = "WEEK") RevenueChartResponse.Mode mode) {
        return ResponseEntity.ok(dashboardService.getRevenueChart(resolveClinicId(auth), mode));
    }
 
    // ── Biểu đồ phân loại tuổi ───────────────────────────────────────────────
 
    /**
     * @param days 7 | 30 | 90 (mặc định 7)
     */
    @GetMapping("/age-chart")
    public ResponseEntity<AgeGroupChartResponse> getAgeChart(
            Authentication auth,
            @RequestParam(defaultValue = "7") int days) {
 
        if (days != 7 && days != 30 && days != 90) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(dashboardService.getAgeGroupChart(resolveClinicId(auth), days));
    }
 
    // ── Heatmap giờ cao điểm ─────────────────────────────────────────────────
 
    /**
     * @param days số ngày nhìn lại để tính tần suất (mặc định 30)
     */
    @GetMapping("/heatmap")
    public ResponseEntity<HeatmapResponse> getHeatmap(
            Authentication auth,
            @RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(dashboardService.getHeatmap(resolveClinicId(auth), days));
    }
 
    // ── Lịch hẹn ─────────────────────────────────────────────────────────────
 
    /**
     * Trả về `limit` lịch hẹn mới nhất (sắp xếp theo ngày tạo giảm dần).
     *
     * @param limit số bản ghi tối đa (mặc định 10, tối đa 50)
     */
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentDashboardResponse>> getRecentAppointments(
            Authentication auth,
            @RequestParam(defaultValue = "10") int limit) {
 
        limit = Math.min(limit, 50);
        return ResponseEntity.ok(dashboardService.getRecentAppointments(resolveClinicId(auth), limit));
    }
 
    /**
     * Trả về lịch hẹn trong ngày mà người dùng click trên date-nav.
     *
     * @param date định dạng yyyy-MM-dd (mặc định hôm nay)
     */
    @GetMapping("/appointments/by-date")
    public ResponseEntity<List<AppointmentDashboardResponse>> getAppointmentsByDate(
            Authentication auth,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
 
        if (date == null) date = LocalDate.now();
        return ResponseEntity.ok(dashboardService.getAppointmentsByDate(resolveClinicId(auth), date));
    }
 
    // ── Đánh giá ─────────────────────────────────────────────────────────────
 
    @GetMapping("/review/recent")
    public ResponseEntity<RecentReviewResponse> getRecentReview(Authentication auth) {
        RecentReviewResponse review = dashboardService.getRecentReview(resolveClinicId(auth));
        if (review == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(review);
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // HELPER
    // ═════════════════════════════════════════════════════════════════════════
 
    /**
     * Lấy maPhongKham của clinic đang đăng nhập từ JWT (maTaiKhoan trong principal).
     * Giả sử principal.getName() trả về maTaiKhoan của AccountEntity.
     */
    private String resolveClinicId(Authentication auth) {
        String maTaiKhoan = auth.getName();
        ClinicEntity clinic = clinicRepository.findByAccount_MaTaiKhoan(maTaiKhoan);
        if (clinic == null) {
            throw new IllegalStateException("Không tìm thấy phòng khám với tài khoản: " + maTaiKhoan);
        }
        return clinic.getMaPhongKham();
    }
}