package com.example.bookingclinic.adminclinic.entity;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "thanh_toan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEntity {

    @Id
    @Column(name = "ma_thanh_toan", length = 255, nullable = false)
    private String maThanhToan;

    @OneToOne
    @JoinColumn(name = "ma_lich_kham")
    private AppointmentScheduleEntity lichKham;

    @Column(name = "so_tien", precision = 18, scale = 2)
    private BigDecimal soTien;

    @Column(name = "phuong_thuc", length = 50)
    private String phuongThuc;

    @Column(name = "ma_giao_dich_tg", length = 255)
    private String maGiaoDichTG;

    @Column(name = "trang_thai_thanh_toan", length = 50)
    private String trangThaiThanhToan;

    @Column(name = "thoi_gian_thanh_toan")
    private LocalDateTime thoiGianThanhToan;
}
