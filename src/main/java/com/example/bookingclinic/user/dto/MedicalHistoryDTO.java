package com.example.bookingclinic.user.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalHistoryDTO {
    // lịch khám
    private String maLichKham;
    private LocalDate ngayKham;
    private String trangThai;
    private Integer danhGia;

    // bác sĩ
    private String maBacSi;
    private String tenBacSi;
    private String hocHam;

    // phòng khám
    private String maPhongKham;
    private String tenPhongKham;
    private String diaChiPhongKham;
    private String sdtPhongKham;

    // chuyên khoa
    private String tenChuyenKhoa;

    // hồ sơ
    private String trieuChung;
    private String chuanDoan;
    private String ketLuan;
    private String ghiChu;
    private LocalDateTime ngayLapHoSo;

    // đơn thuốc
    private String maSoDonThuoc;
    private LocalDateTime ngayLapDon;

    private List<PrescriptionItemDTO> danhSachThuoc;

    // ảnh
    private List<FileImageDTO> images;
}
