package com.example.bookingclinic.doctor.dto.Prescription;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordsDTO {
    private String maHoSo;
    private String maLichKham;
    private String trieuChung;
    private String chuanDoan;
    private String ketLuan;
    private String ghiChu;
    private List<PrescriptionDetailDTO> danhSachThuoc;
}
