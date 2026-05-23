package com.example.bookingclinic.adminclinic.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponse {
    private String maBacSi;
    private String tenBacSi;
    private boolean gioiTinh;
    private String soDienThoai;
    private String email;
    private String diaChi;
    private String tenChuyenKhoa;
    private String kinhNghiem;
    private String chucVu;
    private String hocHam;
    private LocalDateTime ngayDangKy;
}
