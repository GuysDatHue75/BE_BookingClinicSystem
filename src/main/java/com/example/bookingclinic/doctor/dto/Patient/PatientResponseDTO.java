package com.example.bookingclinic.doctor.dto.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.bookingclinic.doctor.dto.Prescription.PrescriptionDTO;
import com.example.bookingclinic.doctor.dto.Prescription.PrescriptionDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDTO {
    private String maBenhNhan;
    // Thông tin sức khỏe (từ bảng Patient)
    private String queQuan;
    private String ngheNghiep;
    private int chieuCao;
    private Double canNang;
    private String tienSuBenhAn;
    private String tinhTrangSucKhoe;
    private String nhomMau;

    // Thông tin cá nhân cơ bản (lấy từ Account nhưng không kèm mã tài khoản/mật
    // khẩu)
    private String hoVaTen;
    private String soDienThoai;
    private String email;
    private LocalDate ngaySinh;
    private Boolean gioiTinh;
    private String diaChi;

    private List<MedicalHistoryDTO> lichSuKham;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicalHistoryDTO {
    private String maHoSo;
    private String maSoDonThuoc;
    private LocalDateTime ngayLap;
    private String trieuChung;
    private String chuanDoan;
    private String ketLuan;
    private String ghiChu;
    private String tenBacSi;
    private List<PrescriptionDetailDTO> danhSachThuoc; // Sẽ rỗng nếu chỉ khámlâm sàng không kê thuốc
    }
}
