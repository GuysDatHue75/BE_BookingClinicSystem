package com.example.bookingclinic.adminclinic.repository.projection;

import java.time.LocalDateTime;
import java.util.List;

import com.example.bookingclinic.adminclinic.dto.response.NotificationIsReadResponse;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationProjection {
    private String maThongBao;
    private String maTaiKhoan;
    private String soDt;
    private String hoVaTen;
    private String tieuDe;
    private String noiDung;
    private String loaiThongBao;
    private String doiTuongNhan;
    private List<NotificationIsReadResponse> danhSachNguoiNhan; 
    private LocalDateTime thoiGianGui;
    private String files;
    private String anhThongBao;

    @QueryProjection
    public NotificationProjection(
        String maThongBao,
        String maTaiKhoan,
        String soDt,
        String hoVaTen,
        String tieuDe,
        String noiDung,
        String loaiThongBao,
        String doiTuongNhan,
        List<NotificationIsReadResponse> danhSachNguoiNhan,
        LocalDateTime thoiGianGui,
        String files,
        String anhThongBao
    ) {
        this.maThongBao = maThongBao;
        this.maTaiKhoan = maTaiKhoan;
        this.soDt = soDt;
        this.hoVaTen = hoVaTen;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.loaiThongBao = loaiThongBao;
        this.doiTuongNhan = doiTuongNhan;
        this.danhSachNguoiNhan = danhSachNguoiNhan;
        this.thoiGianGui = thoiGianGui;
        this.files = files;
        this.anhThongBao = anhThongBao;
    }
}
