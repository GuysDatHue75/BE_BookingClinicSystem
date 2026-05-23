package com.example.bookingclinic.adminclinic.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "phong_kham_chuyen_khoa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecialtyClinicEntity {
    @EmbeddedId
    private SpecialtyClinicId id;

    @ManyToOne
    @MapsId("maPhongKham")
    @JoinColumn(name = "ma_phong_kham")
    private ClinicEntity clinic;

    @ManyToOne
    @MapsId("maChuyenKhoa")
    @JoinColumn(name = "ma_chuyen_khoa")
    private SpecialtyEntity specialty;
}
