package com.example.bookingclinic.adminsystem.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {
    private String maTaiKhoan;
    private String soDt;
    private String vaiTro;
    private String hoVaTen;
    private String email;
    private Boolean trangThai;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
}
