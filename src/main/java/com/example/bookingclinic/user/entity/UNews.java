package com.example.bookingclinic.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
    @Column(length = 10)
    private String maTinTuc;
    private String tieuDe;
    private String noiDung;
    private String maTacGia;
    private LocalDateTime ngayTao;
    private boolean trangThai;
    private String anh;
    private int luotXem;
    @ManyToOne 
    @JoinColumn(name = "ma_phong_kham")
    private UClinic phongKham;
}
