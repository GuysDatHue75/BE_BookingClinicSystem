package com.example.bookingclinic.adminsystem.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tai_khoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEntity {
    @Id
    @Column(name = "ma_tai_khoan", length = 10, nullable = false)
    private String maTaiKhoan;

    @Column(name = "so_dt", length = 20, nullable = false)
    private String soDt;

    @Column(name = "mat_khau", length = 255, nullable = false)
    private String matKhau;

    @Column(name = "vai_tro", length = 50, nullable = false)
    private String vaiTro;

    @Column(name = "ho_va_ten", length = 255, nullable = false)
    private String hoVaTen;

    @Column(name = "anh_dai_dien", length = 255)
    private String anhDaiDien;

    @Column(name = "trang_thai", nullable = false)
    private Boolean trangThai;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat", nullable = false)
    private LocalDateTime ngayCapNhat;

    @Column(name = "ma_phong_kham", length = 10)
    private String maPhongKham;
}
