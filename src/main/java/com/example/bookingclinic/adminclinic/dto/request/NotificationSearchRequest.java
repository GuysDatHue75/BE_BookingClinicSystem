package com.example.bookingclinic.adminclinic.dto.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NotificationSearchRequest {
    private String keyword;
    private Boolean isRead;
    private String maTaiKhoan;

    private String loaiThongBao;
    private String doiTuongNhan;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    private int page = 0;
    private int size = 10;

    private String sortBy = "thoiGianGui";
    private String sortDirection = "desc";
}
