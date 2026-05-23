package com.example.bookingclinic.adminclinic.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDetailResponse {

    private String maBacSi;
    private String tenBacSi;
    private boolean gioiTinh;
    private LocalDate ngaySinh;
    private String queQuan;

    private String soDienThoai;
    private String email;
    private String diaChi;

    private String avt;
    private String maChuyenKhoa;
    private String tenChuyenKhoa;
    private String bangCap;
    private String kinhNghiem;
    private String hoatDong;
    private String mieuTa1;
    private String mieuTa2;

    private String chucVu;
    private String hocHam;

    private String cccd;
    private String soGiayPhep;
    private LocalDateTime ngayCap;
    private String noiCap;

    private String maPhongKham;
    private String tenPhongKham;

    private String maTaiKhoan;
    private String soDt;
    private String matKhau;
    private LocalDateTime ngayDangKy;
    private String tepDinhKem;
}
