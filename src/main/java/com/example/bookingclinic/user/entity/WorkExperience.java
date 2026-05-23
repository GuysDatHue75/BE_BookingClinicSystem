package com.example.bookingclinic.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "kinh_nghiem_cong_tac")
public class WorkExperience {
    @Id
    private Integer maCongTac;
    private Integer namBatDau;
    private Integer namKetThuc;
    private String suKien;
}
