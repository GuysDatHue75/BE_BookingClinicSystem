package com.example.bookingclinic.adminclinic.dto.response.dashboard;

import java.math.BigDecimal;
import java.util.List;
 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
/**
 * Dữ liệu biểu đồ doanh thu (đường).
 * Frontend nhận 1 object duy nhất chứa 3 list song song: labels, income, expense.
 *
 * Ví dụ (mode = WEEK):
 *   labels  : ["CN","T2","T3","T4","T5","T6","T7"]
 *   income  : [800000, 1200000, ...]
 *   expense : [600000, 900000, ...]
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueChartResponse {
 
    public enum Mode { WEEK, MONTH, YEAR }
 
    private Mode mode;
 
    /** Nhãn trục X */
    private List<String> labels;
 
    /** Doanh thu (payment SUCCESS) theo từng nhãn */
    private List<BigDecimal> income;
 
    /**
     * Chi phí ước tính – hiện tại chưa có bảng chi phí nên
     * trả về 70 % income; khi có bảng riêng thì thay thế logic này.
     */
    private List<BigDecimal> expense;
}
