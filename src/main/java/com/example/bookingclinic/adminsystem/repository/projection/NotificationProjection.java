package com.example.bookingclinic.adminsystem.repository.projection;

import java.time.LocalDateTime;
import java.util.List;

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
    private String maNguoiNhan;
    private List<String> danhSachNguoiNhan; 
    private Boolean isRead;
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
        String maNguoiNhan,
        List<String> danhSachNguoiNhan,
        Boolean isRead,
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
        this.maNguoiNhan = maNguoiNhan;
        this.danhSachNguoiNhan = danhSachNguoiNhan;
        this.isRead = isRead;
        this.thoiGianGui = thoiGianGui;
        this.files = files;
        this.anhThongBao = anhThongBao;
    }
    
}
