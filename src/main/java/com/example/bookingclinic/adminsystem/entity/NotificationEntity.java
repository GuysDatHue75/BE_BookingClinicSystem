package com.example.bookingclinic.adminsystem.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "thong_bao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEntity {
    @Id
    @Column(name = "ma_thong_bao", length = 10, nullable = false)
    private String maThongBao;

    @Column(name = "ma_tai_khoan", length = 225, nullable = false)
    private String maTaiKhoan;

    @Column(name = "tieu_de", length = 255, nullable = false)
    private String tieuDe;

    @Lob
    @Column(name = "noi_dung", nullable = false)
    private String noiDung;

    @Column(name = "loai_thong_bao", length= 255, nullable = false)
    private String loaiThongBao;

    @Column(name = "doi_tuong_nhan", length= 255)
    private String doiTuongNhan;

    @Column(name = "thoi_gian_gui", nullable = false)
    private LocalDateTime thoiGianGui;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "files", length = 255)
    private String files;

    @Column(name = "anh_thong_bao", length = 255)
    private String anhThongBao;

}
