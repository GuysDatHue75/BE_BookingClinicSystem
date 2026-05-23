package com.example.bookingclinic.user.entity;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "goi_dang_ky")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Package {

    @Id
    @Column(name = "ma_goi")
    private String maGoi;

    @Column(name = "ten_goi")
    private String tenGoi;

    @Column(name = "gia")
    private Double gia;

    @Column(name = "thoi_han_ngay")
    private Integer thoiHanNgay;

    @Column(name = "mo_ta", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;

    @Column(name = "trang_thai")
    private String trangThai;

    @Builder.Default
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "tinh_nang_goi_dang_ky",
        joinColumns = @JoinColumn(name = "ma_goi"),
        inverseJoinColumns = @JoinColumn(name = "ma_tinh_nang")
    )
    private List<Feature> tinhNangs;
}