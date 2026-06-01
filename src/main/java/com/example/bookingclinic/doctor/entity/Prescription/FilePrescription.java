package com.example.bookingclinic.doctor.entity.Prescription;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "anh_ho_so_kham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilePrescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maAnh;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String tenAnh;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String duongDanAnh;

    @ManyToOne(fetch = FetchType.LAZY)
    // XÓA insertable = false, updatable = false để cho phép lưu ID xuống Database
    @JoinColumn(name = "ma_ho_so", referencedColumnName = "ma_ho_so")
    private MedicalRecords hoSoKham;
}