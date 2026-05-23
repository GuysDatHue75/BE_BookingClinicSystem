package com.example.bookingclinic.adminclinic.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lich_lam_viec")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorScheduleEntity {
    @Id
    @Column(name = "ma_lich_lam", length = 255, nullable = false)
    private String maLichLamViec;

    @ManyToOne @JoinColumn(name = "ma_bac_si")
    private DoctorEntity doctor;

    @ManyToOne @JoinColumn(name = "ma_phong_kham")
    private ClinicEntity clinic;

    @ManyToOne @JoinColumn(name = "ma_khung_gio")
    private TimeSlotsEntity timeslot;
    
    @Column(name = "ngay_lam_viec", nullable = false)
    private LocalDate ngayLamViec;

    @Column(name = "loai_hinh_kham", length = 255)
    private String loaiHinhKham;

    @Column(name = "trang_thai", length = 20, nullable = false)
    private String trangThai;

    @Version
    private Long version;
}
