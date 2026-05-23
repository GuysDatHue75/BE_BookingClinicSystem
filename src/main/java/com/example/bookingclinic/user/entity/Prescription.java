package com.example.bookingclinic.user.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "don_thuoc")
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {
    @Id 
    @Column(length = 10)
    private String maSoDonThuoc;

    @OneToOne 
    @JoinColumn(name = "ma_ho_so")
    private File file;
    
    private LocalDateTime ngayLap;
    @ManyToOne
    @JoinColumn(name = "ma_bac_si")
    private Doctor bacSi;

    @ManyToOne 
    @JoinColumn(name = "ma_benh_nhan")
    private Patient patient;
    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UPrescriptionDetail> danhSachThuoc;
}
