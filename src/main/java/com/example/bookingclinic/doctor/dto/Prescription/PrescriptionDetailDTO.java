package com.example.bookingclinic.doctor.dto.Prescription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDetailDTO {
    private String tenThuoc;
    private String lieuDung;
    private Integer soLuong;
    private String donVi;
    private String ghiChu;
}
