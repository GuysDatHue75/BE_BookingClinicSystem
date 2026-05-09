package com.example.bookingclinic.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tai_khoan")
@Data
public class Account {
    @Id
    @Column(length = 255)
    private String maTaiKhoan;
    private String soDt;
    private String matKhau;
    private String vaiTro;
    private String hoVaTen;
    @Column(name = "anh_dai_dien", columnDefinition = "NVARCHAR(MAX)")
    private String anhDaiDien;
    private Boolean trangThai;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    private String provider;     
    private String providerId;   
    private Integer lanDauDangNhap;
    public Account orElseThrow(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
    }
}
