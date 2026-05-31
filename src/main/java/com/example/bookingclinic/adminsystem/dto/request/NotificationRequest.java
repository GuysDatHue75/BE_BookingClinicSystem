package com.example.bookingclinic.adminsystem.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
    private String doiTuongNhan;
    List<String> danhSachNguoiNhan;
    private LocalDateTime thoiGianGui;
    private MultipartFile files;
    private MultipartFile anhThongBao;
}
