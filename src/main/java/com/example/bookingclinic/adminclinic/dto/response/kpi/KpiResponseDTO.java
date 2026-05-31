package com.example.bookingclinic.adminclinic.dto.response.kpi;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KpiResponseDTO {
    private BigDecimal totalInvoice;
    private Double invoicePercent;
    
    private Long totalPatients;
    private Double patientPercent;
    
    private Long totalAppointments;
    private Double appointmentPercent;
}
