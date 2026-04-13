package com.example.bookingclinic.adminsystem.repository.projection;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BrowseDoctorProjection {
    private String maBacSi;
    private String tenBacSi;
    private boolean gioiTinh;
    private String soDienThoai;
    private String diaChi;
    private String tenPhongKham;
    private String chucVu;
    private String hocHam;
    private String trangThai;
    private LocalDateTime ngayDangKy;
    
    @QueryProjection
    public BrowseDoctorProjection(
        String maBacSi,
        String tenBacSi,
        boolean gioiTinh,
        String soDienThoai,
        String diaChi,
        String tenPhongKham,
        String chucVu,
        String hocHam,
        String trangThai,
        LocalDateTime ngayDangKy
    ) {
        this.maBacSi = maBacSi;
        this.tenBacSi = tenBacSi;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.tenPhongKham = tenPhongKham;
        this.chucVu = chucVu;
        this.hocHam = hocHam;
        this.trangThai = trangThai;
        this.ngayDangKy = ngayDangKy;
    }

}
