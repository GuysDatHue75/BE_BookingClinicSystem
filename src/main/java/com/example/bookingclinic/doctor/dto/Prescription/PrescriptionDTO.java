package com.example.bookingclinic.doctor.dto.Prescription;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDTO {
    private String maSoDonThuoc;
    private LocalDateTime ngayLap;

    // Thông tin từ Hồ sơ khám
    private String trieuChung;
    private String chuanDoan;
    private String ketLuan;
    private String ghiChuHoSo;

    // Thông tin khái quát
    private String tenPhongKham;
    private String tenBacSi;
    private String tenBenhNhan;
    private String sdtBenhNhan;

    // Danh sách thuốc
    private List<PrescriptionDetailDTO> danhSachThuoc;
    private List<String> danhSachFileAnh;
}
