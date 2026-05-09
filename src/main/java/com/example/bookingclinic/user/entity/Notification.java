package com.example.bookingclinic.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "thong_bao")
public class Notification {
    @Id
    private String maThongBao;
    private String tieuDe;
    private String noiDung;
    private String loaiThongBao;
    private String doiTuongNhan;
    private LocalDateTime thoiGianGui;
    private String maNguoiNhan;
    private String anhThongBao;
    private String files;

}
