package com.example.bookingclinic.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Table(name = "thong_bao")
public class Notification {
    @Id
    private String maThongBao;

    private String maTaiKhoan;

    private String tieuDe;
    private String noiDung;
    private String loaiThongBao;
    private String doiTuongNhan;
    private LocalDateTime thoiGianGui;
    private String maNguoiNhan;
    private String anhThongBao;
    private String files;
    @Builder.Default
    private Boolean isDeleted = false;
}
