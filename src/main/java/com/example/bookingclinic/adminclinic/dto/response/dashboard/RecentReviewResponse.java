package com.example.bookingclinic.adminclinic.dto.response.dashboard;

import java.time.LocalDate;
 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
/**
 * Đánh giá gần nhất hiển thị ở góc phải dashboard.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentReviewResponse {
 
    private String maLichKham;
    private String tenBenhNhan;
    private Integer danhGia;          // 1–5 sao
    private LocalDate ngayKham;
    private String tenBacSi;
}
