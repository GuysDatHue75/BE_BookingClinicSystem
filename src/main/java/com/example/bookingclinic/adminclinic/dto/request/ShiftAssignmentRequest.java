package com.example.bookingclinic.adminclinic.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftAssignmentRequest {
    private LocalDate ngayLamViec;
    private String maCaLamViec;
    private List<String> danhSachMaBacSi;
}
