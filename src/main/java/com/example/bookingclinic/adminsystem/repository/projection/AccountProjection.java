package com.example.bookingclinic.adminsystem.repository.projection;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;

@Data
public class AccountProjection {
    private final String maTaiKhoan;
    private final String soDt;
    private final String vaiTro;
    private final String hoVaTen;
    private final String anhDaiDien;
    private final Boolean trangThai;
    private final LocalDateTime ngayTao;
    private final LocalDateTime ngayCapNhat;
    private final String provider;
    private final String email;

    @QueryProjection
    public AccountProjection(
        String maTaiKhoan,
        String soDt,
        String vaiTro,
        String hoVaTen,
        String anhDaiDien,
        Boolean trangThai,
        LocalDateTime ngayTao,
        LocalDateTime ngayCapNhat,
        String provider,
        String email
    ) {
         this.maTaiKhoan = maTaiKhoan;
        this.soDt       = soDt;
        this.vaiTro     = vaiTro;
        this.hoVaTen    = hoVaTen;
        this.anhDaiDien = anhDaiDien;
        this.trangThai  = trangThai;
        this.ngayTao    = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
        this.provider   = provider;
        this.email      = email;
    }
}
