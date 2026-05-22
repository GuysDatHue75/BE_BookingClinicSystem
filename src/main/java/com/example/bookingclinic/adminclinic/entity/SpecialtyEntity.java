package com.example.bookingclinic.adminclinic.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
// import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chuyen_khoa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecialtyEntity {

    @Id
    @Column(name = "ma_chuyen_khoa", length = 10, nullable = false)
    private String maChuyenKhoa;

    @Column(name = "ten_chuyen_khoa", length = 255, nullable = false)
    private String tenChuyenKhoa;

    @Lob
    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "trang_thai")
    private Boolean trangThai;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDateTime ngayTao;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

}
