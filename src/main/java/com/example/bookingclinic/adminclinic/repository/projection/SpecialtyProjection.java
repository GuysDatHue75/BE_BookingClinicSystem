package com.example.bookingclinic.adminclinic.repository.projection;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpecialtyProjection {
    private String maChuyenKhoa;
    private String tenChuyenKhoa;
    private String maPhongKham;
    private String tenPhongKham;
    private String moTa;
    private Boolean trangThai;
    private LocalDateTime ngayTao;

    @QueryProjection
    public SpecialtyProjection(
        String maChuyenKhoa, 
        String tenChuyenKhoa,
        String maPhongKham, 
        String tenPhongKham,
        String moTa, 
        Boolean trangThai, 
        LocalDateTime ngayTao
    ) {
        this.maChuyenKhoa = maChuyenKhoa;
        this.tenChuyenKhoa = tenChuyenKhoa;
        this.maPhongKham = maPhongKham;
        this.tenPhongKham = tenPhongKham;
        this.moTa = moTa;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
    }

}
