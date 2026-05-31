package com.example.bookingclinic.adminclinic.dto.response;

import java.math.BigDecimal;
import java.util.List;

import com.example.bookingclinic.adminclinic.dto.response.dashboard.PatientOverviewDTO;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.SidebarDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
// DTO tổng hợp cho trang Dashboard
public class DashboardResponseDTO {
    private BigDecimal totalRevenueToday;
    private int totalAppointmentsToday;
    private List<RevenueReportDTO> revenueLast7Days;
    private PatientOverviewDTO patientOverview;
    private List<RecentAppointmentDTO> recentAppointments;
    private SidebarDTO sidebarWidgets;
}
