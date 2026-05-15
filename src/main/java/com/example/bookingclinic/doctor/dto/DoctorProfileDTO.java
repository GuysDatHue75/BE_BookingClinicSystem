package com.example.bookingclinic.doctor.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DoctorProfileDTO {
    private String maTaiKhoan;
    private String hoVaTen;
    private String email;
    private String soDienThoai;
    private String anhDaiDien;
    private Boolean gioiTinh;
    private LocalDate ngaySinh;
    private String cccd;
    private String diaChi;

    // --- THÔNG TIN CHUYÊN MÔN (Từ bảng bac_si) ---
    private String maBacSi;
    private String chuyenKhoa;
    private String bangCap;
    private String kinhNghiem;
    private String hoatDong;
    private String mieuTa;
    private String chucVu;
    private String hocHam;
    private String soGiapPhep;
    private LocalDateTime ngayCap;
    private String noiCap;
    private String maPhongKham;
}
