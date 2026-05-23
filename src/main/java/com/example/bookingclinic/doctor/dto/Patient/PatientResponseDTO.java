package com.example.bookingclinic.doctor.dto.Patient;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDTO {
    private String maBenhNhan;
    // Thông tin sức khỏe (từ bảng Patient)
    private String queQuan;
    private String ngheNghiep;
    private int chieuCao;
    private Double canNang;
    private String tienSuBenhAn;
    private String tinhTrangSucKhoe;
    private String nhomMau;

    // Thông tin cá nhân cơ bản (lấy từ Account nhưng không kèm mã tài khoản/mật
    // khẩu)
    private String hoVaTen;
    private String soDienThoai;
    private String email;
    private LocalDate ngaySinh;
    private Boolean gioiTinh;
    private String diaChi;
}
