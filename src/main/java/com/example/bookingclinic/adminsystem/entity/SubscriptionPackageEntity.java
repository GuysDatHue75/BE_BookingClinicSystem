package com.example.bookingclinic.adminsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "goi_dang_ky")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPackageEntity {
    @Id
    @Column(name = "ma_goi", length = 10, nullable = false)
    private String maGoi;

    @Column(name = "ten_goi", length = 255, nullable = false)
    private String tenGoi;

    @Column(name = "gia")
    private Double gia;

    @Column(name = "thoi_han_ngay", nullable = false)
    private int thoiHanNgay;

    @Lob
    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "trang_thai", length= 255, nullable = false)
    private String trangThai;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}