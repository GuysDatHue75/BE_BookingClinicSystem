package com.example.bookingclinic.doctor.dto.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor // Bắt buộc phải có để Hibernate mapping
@AllArgsConstructor // Bắt buộc: Tạo constructor 10 tham số đúng thứ tự câu SELECT
public class ScheduleResponseDTO {
    // 1. a.maLichKham
    private String maLichKham;

    // 2. acc.hoVaTen
    private String hoVaTen;

    // 3. p.ngaySinh
    private LocalDate ngaySinh;

    // 4. p.gioiTinh
    private Boolean gioiTinh;

    // 5. acc.soDt
    private String soDt;

    // 6. p.diaChi
    private String diaChi;

    // 7. a.lyDoKham
    private String lyDoKham;

    // 8. a.trangThai
    private String trangThai;

    // 9. ds.ngayLamViec
    private LocalDate ngayLamViec;

    // 10. ds.khungGioKham.maKhungGio
    private String maKhungGio;

}