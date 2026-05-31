package com.example.bookingclinic.adminclinic.dto.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RevenueReportDTO {
    private String date; // VD: "20-07-2026"
    private BigDecimal income;
}
