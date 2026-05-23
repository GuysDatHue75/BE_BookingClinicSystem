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
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "goi_dang_ky")
public class Package {
    @Id
    @Column(name = "ma_goi")
    private String maGoi;
    private String tenGoi;
    private double gia;
    private int thoiHanNgay;
    private String moTa;
    private String trangThai;
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "tinh_nang_goi_dang_ky",
        joinColumns = @JoinColumn(name = "ma_goi"),
        inverseJoinColumns = @JoinColumn(name = "ma_tinh_nang")
    )
    private List<Feature> tinhNangs;

}
