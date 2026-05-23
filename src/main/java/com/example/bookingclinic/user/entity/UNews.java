package com.example.bookingclinic.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tin_tuc")
public class UNews {
    @Id
    private String maTinTuc;
    private String tieuDe;
    private String moTaNgan;
    private String noiDung;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    private String anh;

    @ManyToOne 
    @JoinColumn(name = "ma_phong_kham")
    private UClinic phongKham;
}
