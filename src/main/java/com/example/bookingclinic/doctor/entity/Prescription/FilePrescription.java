package com.example.bookingclinic.doctor.entity.Prescription;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tai_lieu_kham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilePrescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_tai_lieu")
    private Integer maTaiLieu;
    @Column(name = "ma_ho_so", nullable = false, length = 10)
    private String maHoSo;
    @Column(name = "ten_tai_lieu", nullable = false, length = 255)
    private String tenTaiLieu;
    @Column(name = "file_url", columnDefinition = "NVARCHAR(MAX)")
    private String fileUrl;
    @Column(name = "loai_file", length = 100)
    private String loaiFile;
    @Column(name = "phan_loai", length = 50)
    private String phanLoai; // Ví dụ: XQUANG, SIEUAM, XETNGHIEM
    @Column(name = "ngay_tai_len")
    private LocalDateTime ngayTaiLen;

    @ManyToOne(fetch = FetchType.LAZY)
    // insertable = false, updatable = false dùng để tránh xung đột với biến String
    // maHoSo bạn đã khai báo ở trên
    @JoinColumn(name = "ma_ho_so", referencedColumnName = "ma_ho_so", insertable = false, updatable = false)
    private MedicalRecords hoSoKham;

    @PrePersist
    protected void onCreate() {
        this.ngayTaiLen = LocalDateTime.now();
    }
}