package com.example.bookingclinic.user.entity;

import java.time.LocalDateTime;

import com.example.bookingclinic.doctor.entity.Doctor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tu_van")
public class Advisory {

    @Id
    @Column(name = "ma_tu_van", length = 50)
    private String maTuVan;

    @ManyToOne
    @JoinColumn(name = "ma_bac_si", nullable = false)
    private UDoctor doctor;

    @ManyToOne
    @JoinColumn(name = "ma_benh_nhan", nullable = false)
    private UPatient patient;

    @ManyToOne
    @JoinColumn(name = "ma_phong_kham", nullable = false)
    private UClinic clinic;

    @Column(name = "cau_hoi", nullable = false)
    private String cauHoi;

    @Column(name = "cau_tra_loi")
    private String cauTraLoi;

    @Column(name = "thoi_gian_hoi", nullable = false)
    private LocalDateTime thoiGianHoi;

    @Column(name = "trang_thai_tra_loi")
    private Boolean trangThaiTraLoi;

    public void setThoiGianTraLoi(LocalDateTime now) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setThoiGianTraLoi'");
    }

    public void setTrangThai(boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTrangThai'");
    }
}
