package com.example.bookingclinic.adminclinic.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tin_tuc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsEntity {
    @Id
    @Column(name = "ma_tin_tuc", length = 255, nullable = false)
    private String maTinTuc;

    @Lob
    @Column(name = "tieu_de", nullable = false)
    private String tieuDe;

    @Lob
    @Column(name = "mo_ta_ngan")
    private String moTaNgan;

    @Lob
    @Column(name = "noi_dung")
    private String noiDung;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;

    @Lob
    @Column(name = "anh")
    private String anh;

    @ManyToOne @JoinColumn(name = "ma_phong_kham")
    private ClinicEntity clinic;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}
