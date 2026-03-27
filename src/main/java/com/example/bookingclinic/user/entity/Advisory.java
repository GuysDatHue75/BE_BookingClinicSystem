package com.example.bookingclinic.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tu_van")
public class Advisory {
    @Id
    @Column(length = 10)
    private String maTuVan;
    @OneToOne
    @JoinColumn(name = "ma_bac_si")
    private Doctor doctor;
    @OneToOne
    @JoinColumn(name = "ma_benh_nhan")
    private Patient patient;
    @OneToOne
    @JoinColumn(name = "ma_phong_kham")
    private UClinic clinic;
    private String cauHoi;
    private String cauTraLoi;
    private LocalDateTime thoiGianHoi;
    private LocalDateTime thoiGianTraLoi;
    private Integer trangThaiTraLoi;
}
