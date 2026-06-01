package com.example.bookingclinic.adminclinic.dto.response;

import java.util.List;

import com.example.bookingclinic.adminclinic.dto.response.dashboard.AgeGroupChartResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.AppointmentDashboardResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.DashboardKpiResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.HeatmapResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.RecentReviewResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.RevenueChartResponse;
 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
/**
 * Response tổng hợp cho 1 lần gọi GET /dashboard.
 * Frontend có thể dùng endpoint này để load toàn bộ dữ liệu ban đầu,
 * sau đó gọi riêng từng endpoint khi người dùng thay đổi bộ lọc.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// DTO tổng hợp cho trang Dashboard
public class DashboardResponseDTO {
    private DashboardKpiResponse kpi;
    private AgeGroupChartResponse ageChart;      // mặc định 7 ngày
    private RevenueChartResponse revenueChart;   // mặc định WEEK
    private HeatmapResponse heatmap;             // mặc định 30 ngày gần nhất
    private List<AppointmentDashboardResponse> recentAppointments;
    private RecentReviewResponse recentReview;
}
