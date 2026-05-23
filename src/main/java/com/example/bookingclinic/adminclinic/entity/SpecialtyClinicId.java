package com.example.bookingclinic.adminclinic.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialtyClinicId {
    private String maPhongKham;
    private String maChuyenKhoa;
}
