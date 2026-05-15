package com.example.bookingclinic.doctor.entity.Chat;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "phong_chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    @Column(name = "ma_phong_chat", length = 50)
    private String maPhongChat;

    @Column(name = "ma_nguoi_1", length = 10, nullable = false)
    private String maNguoi1;

    @Column(name = "ma_nguoi_2", length = 10, nullable = false)
    private String maNguoi2;

    @Column(name = "thoi_gian_cap_nhat")
    private LocalDateTime thoiGianCapNhat;
}