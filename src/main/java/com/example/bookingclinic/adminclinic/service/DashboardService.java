package com.example.bookingclinic.adminclinic.service;

import java.time.LocalDate;
import java.util.List;

import com.example.bookingclinic.adminclinic.dto.response.DashboardResponseDTO;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.AgeGroupChartResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.AppointmentDashboardResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.DashboardKpiResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.HeatmapResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.RecentReviewResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.RevenueChartResponse;


public interface DashboardService {
    DashboardResponseDTO getFullDashboard(String maPhongKham);
    DashboardKpiResponse getKpi(String maPhongKham);
    RevenueChartResponse getRevenueChart(String maPhongKham, RevenueChartResponse.Mode mode);
    AgeGroupChartResponse getAgeGroupChart(String maPhongKham, int days);
    HeatmapResponse getHeatmap(String maPhongKham, int days);
    List<AppointmentDashboardResponse> getRecentAppointments(String maPhongKham, int limit);
    List<AppointmentDashboardResponse> getAppointmentsByDate(String maPhongKham, LocalDate date);
    RecentReviewResponse getRecentReview(String maPhongKham);
}
