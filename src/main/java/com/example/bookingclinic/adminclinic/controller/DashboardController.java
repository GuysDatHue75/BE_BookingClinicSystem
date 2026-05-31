package com.example.bookingclinic.adminclinic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminclinic.dto.response.DashboardResponseDTO;
import com.example.bookingclinic.adminclinic.dto.response.kpi.KpiResponseDTO;
import com.example.bookingclinic.adminclinic.service.impl.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/adminclinic/dashboard")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/summary/{maPhongKham}")
    public ResponseEntity<DashboardResponseDTO> getDashboardSummary(@PathVariable String maPhongKham) {
        return ResponseEntity.ok(dashboardService.getDashboardData(maPhongKham));
    }

    @GetMapping("/kpi/{maPhongKham}")
    public ResponseEntity<KpiResponseDTO> getDashboardKpi(@PathVariable String maPhongKham) {
        return ResponseEntity.ok(dashboardService.getKpiData(maPhongKham));
    }
}
