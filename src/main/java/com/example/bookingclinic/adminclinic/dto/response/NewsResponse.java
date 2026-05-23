package com.example.bookingclinic.adminclinic.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsResponse {
    private String maTinTuc;
    private String tieuDe;
    private String moTaNgan;
    private String noiDung;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
}
