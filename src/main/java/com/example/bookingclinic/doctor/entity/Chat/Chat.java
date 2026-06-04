package com.example.bookingclinic.doctor.entity.Chat;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tin_nhan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_tin_nhan")
    private Integer maTinNhan;

    @Column(name = "ma_phong_chat", length = 50, nullable = false)
    private String maPhongChat;

    @Column(name = "ma_nguoi_gui", length = 255, nullable = false)
    private String maNguoiGui;

    @Column(name = "ma_nguoi_nhan", length = 255, nullable = false)
    private String maNguoiNhan;

    @Column(name = "loai_tin_nhan", length = 20)
    private String loaiTinNhan = "TEXT";

    @Column(name = "noi_dung", columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String noiDung;

    @Column(name = "thoi_gian_gui")
    private LocalDateTime thoiGianGui;

    @Column(name = "da_xem")
    private Boolean daXem = false;

    // Tự động gán thời gian trước khi lưu xuống DB
    @PrePersist
    protected void onCreate() {
        this.thoiGianGui = LocalDateTime.now();
    }
}