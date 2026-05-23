package com.example.bookingclinic.user.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItemDTO  {

    private String tenThuoc;
    private String lieuDung;
    private Integer soLuong;
    private BigDecimal donGia;
    private String donVi;
    private String ghiChu;
}
