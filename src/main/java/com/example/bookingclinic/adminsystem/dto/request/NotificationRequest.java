package com.example.bookingclinic.adminsystem.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {
    private String maThongBao;
    private String maTaiKhoan;
    private String tieuDe;
    private String noiDung;
    private String loaiThongBao;
    private String doituongNhan;
    List<String> danhSachNguoiNhan;
    private LocalDateTime thoiGianGui;
}
