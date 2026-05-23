package com.example.bookingclinic.user.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chi_tiet_don_thuoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UPrescriptionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_chi_tiet")
    private Integer maChiTiet;

    @ManyToOne
    @JoinColumn(name = "ma_so_don_thuoc", nullable = false)
    @JsonIgnore
    private UPrescription prescription;

    @Column(name = "ten_thuoc", nullable = false, length = 255)
    private String tenThuoc;

    @Column(name = "lieu_dung", length = 255)
    private String lieuDung;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "don_gia", precision = 18, scale = 2)
    private BigDecimal donGia;

    @Column(name = "don_vi", length = 50)
    private String donVi;

    @Column(name = "ghi_chu")
    private String ghiChu;
}