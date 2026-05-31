package com.example.bookingclinic.adminsystem.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrowseClinicResponse {
    private String maPhongKham;
    private String tenPhongKham;
    private String nguoiDaiDien;
    private String loaiHinhPhongKham;
    private String diaChi;
    private String tinhThanhPho;
    private Integer soLuongBacSi;
    private String trangThai;
    private LocalDateTime ngayDangKy;
    private String maGoi;
    private String email;
}
