package com.example.bookingclinic.adminclinic.dto.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DoctorSearchRequest {
    private String keyword;
    private String maChuyenKhoa;
    private String kinhNghiem;
    private String chucVu;
    private String hocHam;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
