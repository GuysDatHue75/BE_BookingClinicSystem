package com.example.bookingclinic.adminclinic.dto.response.dashboard;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientOverviewDTO {
    private List<String> categories; // Trục X: ["4 Jul", "5 Jul"...]
    private List<Long> childData;
    private List<Long> adultData;
    private List<Long> elderlyData;
}
