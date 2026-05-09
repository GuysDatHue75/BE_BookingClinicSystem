package com.example.bookingclinic.doctor.dto.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 2. DTO bọc ngoài cùng (Hứng cục JSON tổng)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyScheduleRequestDTO {
    private String maBacSi;

    // CHÚ Ý: ĐẦU VÀO LUÔN LÀ LIST (Bám sát JSON mà UI gửi lên)
    private List<DailyScheduleDTO> danhSachNgayLamViec;

}