package com.example.bookingclinic.adminclinic.repository.projection;
import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;

@Data
public class DoctorProjection {

    private final String maBacSi;
    private final String tenBacSi;
    private final boolean gioiTinh;

    private final String soDienThoai;
    private final String email;
    private final String diaChi;

    private final String avt;
    private final String maChuyenKhoa;
    private final String tenChuyenKhoa;
    private final String bangCap;
    private final String kinhNghiem;
    private final String hoatDong;
    private final String mieuTa;

    private final String chucVu;
    private final String hocHam;

    private final String cccd;
    private final String soGiayPhep;
    private final LocalDateTime ngayCap;
    private final String noiCap;

    private final String maPhongKham;
    private final String tenPhongKham;

    private final String maTaiKhoan;
    private final String soDt;
    private final String matKhau;
    private final LocalDateTime ngayDangKy;
    private final String tepDinhKem;

    @QueryProjection
    public DoctorProjection (
        String maBacSi,
        String tenBacSi,
        boolean gioiTinh,
        String soDienThoai,
        String email,
        String diaChi,
        String avt,
        String maChuyenKhoa,
        String tenChuyenKhoa,
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
        String maPhongKham,
        String tenPhongKham,
        String maTaiKhoan,
        String soDt,
        String matKhau,
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
        this.maChuyenKhoa = maChuyenKhoa;
        this.tenChuyenKhoa = tenChuyenKhoa;
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
        this.maPhongKham = maPhongKham;
        this.tenPhongKham = tenPhongKham;
        this.maTaiKhoan = maTaiKhoan;
        this.soDt = soDt;
        this.matKhau = matKhau;
        this.ngayDangKy = ngayDangKy;
        this.tepDinhKem = tepDinhKem;
    }
}