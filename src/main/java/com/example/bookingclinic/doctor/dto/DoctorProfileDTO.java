package com.example.bookingclinic.doctor.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DoctorProfileDTO {

    // --- 1. THÔNG TIN ĐỊNH DANH (Từ bảng tai_khoan) ---
    private String maTaiKhoan;
    private String hoVaTen;

    // --- 2. THÔNG TIN CÁ NHÂN (Từ bảng bac_si) ---
    private String maBacSi;
    private String email;
    private String soDienThoai;
    @JsonProperty("anhDaiDien")
    private String anhDaiDien; // Trong service đang map với trường 'avt'
    private Boolean gioiTinh;
    private LocalDate ngaySinh;
    private String cccd;
    private String diaChi;

    // --- 3. THÔNG TIN CHUYÊN MÔN (Từ bảng bac_si) ---
    private String maChuyenKhoa; // Dùng để FE gửi ID xuống khi Update (PUT)
    private String tenChuyenKhoa; // Dùng để FE hiển thị tên chuyên khoa (GET)

    private String bangCap;
    private String kinhNghiem;
    private String hoatDong;
    private String mieuTa1;
    private String mieuTa2;
    private String chucVu;
    private String hocHam;

    // --- 4. THÔNG TIN GIẤY PHÉP & ĐƠN VỊ CÔNG TÁC ---
    private String soGiayPhep; // Sếp check lại database xem là soGiayPhep hay soGiapPhep nhé (sai chính tả 1
                               // chữ là lỗi ráng chịu nha :D)
    private LocalDateTime ngayCap;
    private String noiCap;
    private String maPhongKham;
}