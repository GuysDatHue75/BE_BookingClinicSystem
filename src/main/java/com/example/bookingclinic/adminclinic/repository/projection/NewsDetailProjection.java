package com.example.bookingclinic.adminclinic.repository.projection;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsDetailProjection {
    private String maTinTuc;
    private String tieuDe;
    private String moTaNgan;
    private String noiDung;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    private String anh;
    private String tenPhongKham;

    @QueryProjection
    public NewsDetailProjection(
        String maTinTuc,
        String tieuDe,
        String moTaNgan,
        String noiDung,
        LocalDateTime ngayTao,
        LocalDateTime ngayCapNhat,
        String anh,
        String tenPhongKham
    ) {
        this.maTinTuc = maTinTuc;
        this.tieuDe = tieuDe;
        this.moTaNgan = moTaNgan;
        this.noiDung = noiDung;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
        this.anh = anh;
        this.tenPhongKham = tenPhongKham;
    }
}
