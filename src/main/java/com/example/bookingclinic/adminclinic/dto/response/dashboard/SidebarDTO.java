package com.example.bookingclinic.adminclinic.dto.response.dashboard;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SidebarDTO {
    private List<HeatmapRowDTO> heatmap;
    private RecentReviewDTO recentReview;

    @Data
    @Builder
    public static class HeatmapRowDTO {
        private String dayOfWeek; // VD: "T2", "T3"
        private List<Integer> density; // Mảng 5 phần tử tương ứng 5 khung giờ (0: Ít, 1: TB, 2: Cao)
    }

    @Data
    @Builder
    public static class RecentReviewDTO {
        private String patientName;
        private Integer rating; // Số sao (1-5)
        private String date;
        private String comment; // Nội dung đánh giá
    }
}
