package com.example.bookingclinic.doctor.dto.Chat;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Thêm Builder để lúc map dữ liệu cho nhanh sếp nhé
public class ChatMessageDTO {
    private Integer maTinNhan;
    private String maPhongChat;
    private String maNguoiGui;
    private String maNguoiNhan;
    private String loaiTinNhan;
    private String noiDung;
    private LocalDateTime thoiGianGui;
    private String avatarNguoiGui;
    private Boolean daXem;

}