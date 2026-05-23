package com.example.bookingclinic.adminsystem.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPackageRequest {
    private String maGoi;
    private String tenGoi;
    private Double gia;
    private Integer thoiHanNgay;
    private String moTa;
    private String trangThai;
    private List<String> danhSachMaTinhNang;
    
}
