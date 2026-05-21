package com.example.bookingclinic.adminsystem.dto.response;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubscriptionPackageResponse {
    private String maGoi;
    private String tenGoi;
    private Double gia;
    private Integer thoiHanNgay;
    private String moTa;
    private String trangThai;
    private List<FeaturesDto> danhSachTenTinhNang;
}
