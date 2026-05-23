package com.example.bookingclinic.doctor.entity.Prescription;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chi_tiet_don_thuoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_chi_tiet")
    private Integer maChiTiet;

    // Khóa ngoại liên kết tới bảng don_thuoc
    @ManyToOne
    @JoinColumn(name = "ma_so_don_thuoc", nullable = false)
    @JsonIgnore // Tránh lỗi vòng lặp khi render JSON
    private Prescription prescription;

    @Column(name = "ten_thuoc", length = 255, nullable = false)
    private String tenThuoc;

    @Column(name = "lieu_dung", length = 255)
    private String lieuDung;

    @Column(name = "so_luong")
    private Integer soLuong;

    // --- TRƯỜNG CÒN THIẾU TRONG SQL ---
    @Column(name = "don_gia")
    private Double donGia; // Map với numeric(38,2)

    @Column(name = "don_vi", length = 50)
    private String donVi;

    @Column(name = "ghi_chu", length = 255)
    private String ghiChu;
}  