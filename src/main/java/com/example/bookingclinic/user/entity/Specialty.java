package com.example.bookingclinic.user.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "chuyen_khoa")
public class Specialty {
    @Id
    @Column(length = 10)
    private String maChuyenKhoa;
    private String tenChuyenKhoa;
    private String maTa;
    private int trangThai;
    private LocalDate ngayTao;
    @ManyToMany(mappedBy = "specicaltys")
    @JsonIgnore
    private List<UClinic> clinics;
}
