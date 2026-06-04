package com.example.bookingclinic.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "kinh_nghiem_cong_tac")
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperience {
    @Id
    @Column(name = "ma_cong_tac")
    private Integer maCongTac;
    @Column(name = "nam_bat_dau")
    private Integer namBatDau;
    @Column(name = "ma_bac_si")
    private String maBacSi;
    @Column(name = "nam_ket_thuc")
    private Integer namKetThuc;
    @Column(name = "su_kien")
    private String suKien;
}
