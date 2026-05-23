package com.example.bookingclinic.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "thong_bao")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Notification {

    @Id
    @Column(name = "ma_thong_bao")
    private String maThongBao;

    @Column(name = "ma_tai_khoan")
    private String maTaiKhoan;

    @Column(name = "tieu_de")
    private String tieuDe;

    @Column(name = "noi_dung")
    private String noiDung;

    @Column(name = "loai_thong_bao")
    private String loaiThongBao;

    @Column(name = "doi_tuong_nhan")
    private String doiTuongNhan;

    @Column(name = "thoi_gian_gui")
    private LocalDateTime thoiGianGui;

    @Column(name = "ma_nguoi_nhan")
    private String maNguoiNhan;

    @Column(name = "anh_thong_bao")
    private String anhThongBao;

    @Column(name = "files")
    private String files;

    @Builder.Default
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
}