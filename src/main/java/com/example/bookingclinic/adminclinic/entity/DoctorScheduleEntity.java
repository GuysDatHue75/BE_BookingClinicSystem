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
@Table(name = "lich_kham_bac_si")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorScheduleEntity {
    @Id
    @Column(name = "ma_lich_lam_viec", length = 255, nullable = false)
    private String maLichLamViec;

    @ManyToOne @JoinColumn(name = "ma_bac_si")
    private DoctorEntity doctor;

    @ManyToOne @JoinColumn(name = "ma_phong_kham")
    private ClinicEntity clinic;
    
    @Column(name = "ngay_lam_viec", nullable = false)
    private LocalDate ngayLamViec;
    

    @ManyToOne @JoinColumn(name = "ma_khung_gio")
    private TimeSlotsEntity timeslot;

    @Column(name = "trang_thai", length = 20, nullable = false)
    private String trangThai;

    @Version
    private Long version;
}
