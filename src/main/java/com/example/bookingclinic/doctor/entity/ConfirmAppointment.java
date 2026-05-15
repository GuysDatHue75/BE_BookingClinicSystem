package com.example.bookingclinic.doctor.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "lich_kham")
@Data
public class ConfirmAppointment {
    @Id
    private String maLichKham;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_tai_khoan", columnDefinition = "NVARCHAR(10)")
    private Account taiKhoan;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_lich_lam")
    private DoctorSchedule lichLamViec;
    private String lyDoKham;
    private String trangThai;
    private LocalDateTime ngayTao;
}