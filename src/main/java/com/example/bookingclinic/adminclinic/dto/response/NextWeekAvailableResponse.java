package com.example.bookingclinic.adminclinic.dto.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NextWeekAvailableResponse {
    private LocalDate startDate;
    private LocalDate endDate;
}
