package com.example.bookingclinic.doctor.entity.Prescription;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class PrescriptionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maChiTiet;

    // Khóa ngoại liên kết tới bảng don_thuoc
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_so_don_thuoc", nullable = false)
    @JsonIgnore // Tránh lỗi vòng lặp khi render JSON
    private Prescription prescription;
    private String tenThuoc;
    private String lieuDung;
    private Integer soLuong;
    private String donVi;
    private String ghiChu;
}