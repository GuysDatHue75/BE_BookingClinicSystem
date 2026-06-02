package com.example.bookingclinic.adminclinic.dto.response.dashboard;

import java.util.List;
 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
/**
 * Dữ liệu biểu đồ cột phân loại bệnh nhân theo độ tuổi.
 *
 * Phân nhóm tuổi:
 *   Trẻ em  : < 18 tuổi
 *   Người lớn: 18 – 60 tuổi
 *   Cao tuổi : > 60 tuổi
 *
 * Hỗ trợ filter: 7 | 30 | 90 ngày (truyền qua query param "days")
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgeGroupChartResponse {
 
    /** Nhãn ngày / tuần / tháng trên trục X */
    private List<String> labels;
 
    /** Số lượt khám của nhóm trẻ em tương ứng với mỗi label */
    private List<Long> treEm;
 
    /** Số lượt khám của nhóm người lớn */
    private List<Long> nguoiLon;
 
    /** Số lượt khám của nhóm cao tuổi */
    private List<Long> caoTuoi;
 
    /** Tổng từng nhóm (dùng cho tooltip legend) */
    private long tongTreEm;
    private long tongNguoiLon;
    private long tongCaoTuoi;
}
