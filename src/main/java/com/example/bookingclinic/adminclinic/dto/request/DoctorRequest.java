package com.example.bookingclinic.adminclinic.dto.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorRequest {

    private String maTaiKhoan;
    private String tenBacSi;
    private boolean gioiTinh;

    private String soDienThoai;
    private String email;
    private String diaChi;

    private String avt;
    private String maChuyenKhoa;
    private String bangCap;
    private String kinhNghiem;
    private String hoatDong;
    private String mieuTa;

    private String chucVu;
    private String hocHam;

    private String cccd;
    private String soGiayPhep;
    private LocalDateTime ngayCap;
    private String noiCap;

    private String tepDinhKem;
}