package com.example.bookingclinic.doctor.entity.Prescription;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "don_thuoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {

    @Id
    private String maSoDonThuoc;
    private LocalDateTime ngayLap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_ho_so", insertable = false, updatable = false)
    private MedicalRecords medicalRecord;

    // CascadeType.ALL (Cha chết thì Con chết theo)
    // orphanRemoval = true (Cha gạch tên thì Con chết)
    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionDetail> chiTietDonThuoc;
}