package com.example.bookingclinic.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "chuong_trinh_dao_tao")
public class TraningProgram {
    @Id
    @Column(name = "ma_dao_tao")
    private Integer maDaoTao;
    @Column(name = "nam_bat_dau")
    private Integer namBatDau;
    @Column(name = "ma_bac_si")
    private String maBacSi;
    @Column(name = "nam_ket_thuc")
    private Integer namKetThuc;
    @Column(name = "su_kien")
    private String suKien;
}