package com.example.bookingclinic.doctor.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "tu_van")
@Data
public class Advise {
    @Id
    private String maTuVan;

    private String maBenhNhan;

    private String maBacSi;

    private String maPhongKham;

    private String cauHoi;

    private String cauTraLoi;

    private LocalDateTime thoiGianHoi;

    private LocalDateTime thoiGianTraLoi;

    @Column(name = "trang_thai", nullable = false)
    private Boolean trangThai = false; // 0: Chưa trả lời, 1: Đã trả lời
}
