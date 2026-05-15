package com.example.bookingclinic.doctor.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "benh_nhan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "maBenhNhan",
        "hoVaTen",
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
    @Column(name = "maBenhNhan")
    @NotBlank(message = "Mã bệnh nhân không được để trống")
    private String maBenhNhan;

    private String queQuan;
    private String ngheNghiep;
    private Integer chieuCao;
    private Double canNang;
    private String nhomMau;
    private String tienSuBenhAn;
    private String tinhTrangSucKhoe;

    // Khóa ngoại kết nối 1-1 với Account
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_tai_khoan")
    // @JsonIgnore
    @JsonManagedReference
    private Account taiKhoan;

    // Helper method lấy tên từ bảng Account để hiển thị JSON đẹp hơn
    public String getHoVaTen() {
        return (taiKhoan != null) ? taiKhoan.getHoVaTen() : null;
    }
}