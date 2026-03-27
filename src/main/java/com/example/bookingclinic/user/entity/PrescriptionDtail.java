package com.example.bookingclinic.user.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Table(name = "chi_tiet_don_thuoc") 
@Data
public class PrescriptionDtail {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maChiTiet;
    @ManyToOne 
    @JoinColumn(name = "ma_so_don_thuoc")
    @JsonIgnore
    private Prescription prescription;
    private String tenThuoc;
    private String lieuDung;
    private Integer soLuong;
    private BigDecimal donGia;
    private String donVi;
    private String ghiChu;
}
