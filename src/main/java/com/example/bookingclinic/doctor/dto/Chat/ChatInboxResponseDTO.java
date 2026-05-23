package com.example.bookingclinic.doctor.dto.Chat;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatInboxResponseDTO {
    private String maPhongChat;
    private String maDoiPhuong; // ID người nhắn với mình
    private String tenDoiPhuong; // Lấy từ bảng Account (để hiển thị tên)
    private String tinNhanCuoi;
    private LocalDateTime thoiGianCuoi;
    private long soTinChuaDoc;
    private String maNguoiGuiCuoi; // Ai là người gửi tin cuối cùng?
    private Boolean daXemCuoi; // Tổng tin nhắn maNguoiNhan = me và daXem = false
}