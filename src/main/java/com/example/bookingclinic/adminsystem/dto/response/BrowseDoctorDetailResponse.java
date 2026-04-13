package com.example.bookingclinic.adminsystem.dto.response;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BrowseDoctorDetailResponse {

    private String maBacSi;
    private String tenBacSi;
    private boolean gioiTinh;

    private String soDienThoai;
    private String email;
    private String diaChi;

    private String avt;
    private String chuyenKhoa;
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

    private String trangThai;
    private String maPhongKham;
    private String tenPhongKham;

    private LocalDateTime ngayDangKy;
    private String tepDinhKem;

    @QueryProjection
    public BrowseDoctorDetailResponse(
        String maBacSi,
        String tenBacSi,
        boolean gioiTinh,
        String soDienThoai,
        String email,
        String diaChi,
        String avt,
        String chuyenKhoa,
        String bangCap,
        String kinhNghiem,
        String hoatDong,
        String mieuTa,
        String chucVu,
        String hocHam,
        String cccd,
        String soGiayPhep,
        LocalDateTime ngayCap,
        String noiCap,
        String trangThai,
        String maPhongKham,
        String tenPhongKham,
        LocalDateTime ngayDangKy,
        String tepDinhKem
    ) {
        this.maBacSi = maBacSi;
        this.tenBacSi = tenBacSi;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.avt = avt;
        this.chuyenKhoa = chuyenKhoa;
        this.bangCap = bangCap;
        this.kinhNghiem = kinhNghiem;
        this.hoatDong = hoatDong;
        this.mieuTa = mieuTa;
        this.chucVu = chucVu;
        this.hocHam = hocHam;
        this.cccd = cccd;
        this.soGiayPhep = soGiayPhep;
        this.ngayCap = ngayCap;
        this.noiCap = noiCap;
        this.trangThai = trangThai;
        this.maPhongKham = maPhongKham;
        this.tenPhongKham = tenPhongKham;
        this.ngayDangKy = ngayDangKy;
        this.tepDinhKem = tepDinhKem;
    }
}