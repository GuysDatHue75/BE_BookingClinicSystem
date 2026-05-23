package com.example.bookingclinic.adminclinic.dto.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecialtyRequest {
    
    private String tenChuyenKhoa;
    private String moTa;
    private LocalDateTime ngayTao;
    private Boolean isDeleted;

}
