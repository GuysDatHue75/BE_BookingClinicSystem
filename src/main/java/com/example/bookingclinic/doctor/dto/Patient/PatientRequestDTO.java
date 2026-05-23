package com.example.bookingclinic.doctor.dto.Patient;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequestDTO {
    // 1. Mã bệnh nhân để tìm kiếm người dùng đã đăng ký
    private String maBenhNhan;

    private LocalDate ngaySinh;
    private String queQuan;
    private String soDienThoai;
    private String ngheNghiep;
    private int chieuCao;
    private Double canNang;
    private String tienSuBenhAn;
    private String tinhTrangSucKhoe;
    private String nhomMau;
    private Boolean gioiTinh;
    private String email;
    private String diaChi;
}
