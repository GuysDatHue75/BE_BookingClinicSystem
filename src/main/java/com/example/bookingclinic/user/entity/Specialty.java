package com.example.bookingclinic.user.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "chuyen_khoa")
@AllArgsConstructor
@NoArgsConstructor
public class Specialty {

    @Id
    @Column(name = "ma_chuyen_khoa", length = 10)
    private String maChuyenKhoa;

    @Column(name = "ten_chuyen_khoa")
    private String tenChuyenKhoa;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "trang_thai")
    private Boolean trangThai;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @ManyToMany(mappedBy = "specicaltys")
    @JsonIgnore
    private List<UClinic> clinics;

    @Builder.Default
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
}
