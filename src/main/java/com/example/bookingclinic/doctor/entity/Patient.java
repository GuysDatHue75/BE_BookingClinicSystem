package com.example.bookingclinic.doctor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "benh_nhan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "maBenhNhan",
        "hoVaTen",
        "ngaySinh",
        "gioiTinh",
        "soDienThoai",
        "email",
        "diaChi",
        "queQuan",
        "ngheNghiep",
        "chieuCao",
        "canNang",
        "nhomMau",
        "tienSuBenhAn",
        "tinhTrangSucKhoe"
})
public class Patient {

    @Id
    @Column(name = "ma_benh_nhan", length = 255)
    @NotBlank(message = "Mã bệnh nhân không được để trống")
    private String maBenhNhan;

    // --- CÁC TRƯỜNG BỔ SUNG TỪ SQL ---

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Column(name = "so_dien_thoai", length = 50)
    private String soDienThoai;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh; // bit trong SQL map sang Boolean

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "dia_chi", length = 255)
    private String diaChi;

    // --- CÁC TRƯỜNG CŨ ĐÃ MAP LẠI TÊN CỘT CHO CHẮC CỐP ---

    @Column(name = "que_quan", length = 255)
    private String queQuan;

    @Column(name = "nghe_nghiep", length = 255)
    private String ngheNghiep;

    @Column(name = "chieu_cao")
    private Integer chieuCao;

    @Column(name = "can_nang")
    private Double canNang; // numeric(38,2) dùng Double hoặc BigDecimal

    @Column(name = "nhom_mau", length = 255)
    private String nhomMau;

    @Column(name = "tien_su_benh_an", length = 255)
    private String tienSuBenhAn;

    @Column(name = "tinh_trang_suc_khoe", length = 255)
    private String tinhTrangSucKhoe;

    // --- MỐI QUAN HỆ ---

    // Khóa ngoại kết nối 1-1 với Account
    @OneToOne
    @JoinColumn(name = "ma_tai_khoan")
    @JsonManagedReference
    @JsonIgnore
    private Account taiKhoan;

    // Helper method lấy tên từ bảng Account để hiển thị JSON đẹp hơn
    public String getHoVaTen() {
        return (taiKhoan != null) ? taiKhoan.getHoVaTen() : null;
    }
}