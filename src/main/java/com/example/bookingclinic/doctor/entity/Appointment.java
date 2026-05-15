package com.example.bookingclinic.doctor.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lich_kham")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    private String maLichKham;

    private String lyDoKham;

    private LocalDateTime ngayTao;

    private String trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_lich_lam", columnDefinition = "nvarchar(10)", nullable = false)
    private DoctorSchedule lichLamViec;

    // Đổi tên biến thành benhNhan (thay vì maBenhNhan)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maBenhNhan", columnDefinition = "nvarchar(10)", nullable = false)
    private Patient benhNhan;

    // Đổi tên biến thành taiKhoan (thay vì maTaiKhoan)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_tai_khoan", columnDefinition = "nvarchar(10)")
    private Account taiKhoan;
}