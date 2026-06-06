package com.example.bookingclinic.adminsystem.dto.request;

import org.springframework.web.multipart.MultipartFile;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {
    private String soDt;
    private String matKhau;    
    private String vaiTro;
    private String hoVaTen;
    private String email;
    private Boolean trangThai;
    private MultipartFile anhDaiDien;
}
