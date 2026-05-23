package com.example.bookingclinic.doctor.entity.Prescription;

import java.time.LocalDateTime;
import java.util.List;

// Import 2 Entity Doctor và Patient để map khóa ngoại
import com.example.bookingclinic.doctor.entity.Doctor;
import com.example.bookingclinic.doctor.entity.Patient;

import jakarta.persistence.*;
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
    @Column(name = "ma_so_don_thuoc", length = 10)
    private String maSoDonThuoc;

    @Column(name = "ngay_lap")
    private LocalDateTime ngayLap;

    @ManyToOne
    @JoinColumn(name = "ma_ho_so", nullable = false)
    private MedicalRecords medicalRecord;

    // --- BỔ SUNG 2 KHÓA NGOẠI TỪ SQL ---

    @ManyToOne
    @JoinColumn(name = "ma_bac_si")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "ma_benh_nhan")
    private Patient patient;

    // --- MỐI QUAN HỆ VỚI CHI TIẾT ---

    // CascadeType.ALL (Cha chết thì Con chết theo)
    // orphanRemoval = true (Cha gạch tên thì Con chết)
    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionDetail> chiTietDonThuoc;
}