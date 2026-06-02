package com.example.bookingclinic.adminclinic.dto.response.dashboard;

import java.util.List;
 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
/**
 * Ma trận nhiệt (heatmap) giờ cao điểm lịch hẹn.
 *
 * rows  : 7 hàng = T2 → CN  (0 = Monday … 6 = Sunday)
 * cols  : 11 cột = 8h → 18h (mỗi slot 1 tiếng)
 *
 * data[dayIndex][hourIndex] = số lịch hẹn
 *
 * Frontend dùng để tô màu ô theo mức:
 *   0–2   → ít     (#e1f5ee)
 *   3–5   → TB     (#1D9E75)
 *   6+    → cao    (#085041)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeatmapResponse {
 
    /** T2 → CN */
    private List<String> dayLabels;
 
    /** 8h → 18h */
    private List<Integer> hourLabels;
 
    /**
     * Ma trận [dayIndex][hourIndex].
     * Kích thước cố định: 7 × 11
     */
    private List<List<Long>> data;
 
    /** Giá trị max trong toàn bộ ma trận – frontend dùng để normalize màu */
    private long maxValue;
}
