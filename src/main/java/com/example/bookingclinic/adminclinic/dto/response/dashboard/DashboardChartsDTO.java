package com.example.bookingclinic.adminclinic.dto.response.dashboard;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardChartsDTO {
    private PatientOverviewChartDTO patientOverview;
    private List<RevenueChartDTO> revenueChart;

    @Data
    @Builder
    public static class PatientOverviewChartDTO {
        private List<String> categories; // Chứa trục X: ["4 Jul", "5 Jul"...]
        private List<Long> childData;
        private List<Long> adultData;
        private List<Long> elderlyData;
    }

    @Data
    @Builder
    public static class RevenueChartDTO {
        private String date;
        private BigDecimal income;
    }
}
