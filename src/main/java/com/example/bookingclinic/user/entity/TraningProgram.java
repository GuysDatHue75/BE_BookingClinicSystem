package com.example.bookingclinic.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "chuong_trinh_dao_tao")
public class TraningProgram {
    @Id
    private Integer maDaoTao;
    private Integer namBatDau;
    private Integer namKetThuc;
    private String suKien;
}