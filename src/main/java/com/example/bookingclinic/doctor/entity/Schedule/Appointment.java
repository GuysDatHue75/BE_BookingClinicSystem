package com.example.bookingclinic.doctor.entity.Schedule;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.bookingclinic.doctor.entity.Doctor;
import com.example.bookingclinic.doctor.entity.Patient;

@Entity
@Table(name = "lich_kham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @Column(name = "ma_lich_kham", length = 255)
    private String maLichKham;

    @Column(name = "ngay_kham", nullable = false)
    private LocalDate ngayKham; // Ngày hẹn khám do bệnh nhân chọn từ lịch bác sĩ

    @Column(name = "loai_kham", length = 100, nullable = false)
    private String loaiKham; // Ví dụ: Khám nội, khám ngoại, khám tổng quát

    @Column(name = "ly_do_kham", columnDefinition = "NVARCHAR(MAX)")
    private String lyDoKham; // Lý do khám do Bệnh nhân nhập

    @Column(name = "gio_kham")
    private LocalTime gioKham; // Giờ khám cụ thể

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao; // Thời điểm bệnh nhân bấm đặt lịch

    @Column(name = "trang_thai", length = 50)
    private String trangThai;

    @Column(name = "danh_gia")
    private Integer danhGia; // Bệnh nhân đánh giá sau khi khám xong (1-5 sao)

    @Column(name = "da_gui_thong_bao")
    private Integer daGuiThongBao;

    // --- KHÓA NGOẠI KẾT NỐI ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_lich_lam")
    private DoctorSchedule lichLamViec; // Trỏ tới lịch làm việc gốc của bác sĩ

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_benh_nhan", nullable = false)
    private Patient benhNhan; // Ai đặt lịch?

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_bac_si", nullable = false)
    private Doctor bacSi; // Đặt lịch với bác sĩ nào?

}