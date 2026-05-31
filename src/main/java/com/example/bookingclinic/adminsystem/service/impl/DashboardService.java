// package com.example.bookingclinic.adminsystem.service.impl;

// import java.math.BigDecimal;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.LocalTime;
// import java.util.List;
// import java.util.Map;
// import java.util.stream.Collectors;

// import org.springframework.stereotype.Service;

// import com.example.bookingclinic.adminclinic.dto.response.DashboardResponseDTO;
// import com.example.bookingclinic.adminclinic.dto.response.RecentAppointmentDTO;
// import com.example.bookingclinic.adminclinic.dto.response.RevenueReportDTO;
// import com.example.bookingclinic.adminclinic.entity.AppointmentScheduleEntity;
// import com.example.bookingclinic.adminclinic.entity.PaymentEntity;
// import com.example.bookingclinic.adminclinic.repository.custom.DashboardRepositoryCustom;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class DashboardService {
//     private final DashboardRepositoryCustom dashboardRepo;

//     public DashboardResponseDTO getDashboardData() {
//         LocalDateTime now = LocalDateTime.now();
//         LocalDateTime sevenDaysAgo = now.minusDays(7).with(LocalTime.MIN);

//         // 1. Lấy dữ liệu 7 ngày qua
//         List<PaymentEntity> recentPayments = dashboardRepo.getPaymentsBetween(sevenDaysAgo, now);

//         // 2. Xử lý biểu đồ Doanh thu (Dùng Stream API để Group by ngày)
//         Map<LocalDate, BigDecimal> revenueByDate = recentPayments.stream()
//                 .collect(Collectors.groupingBy(
//                         p -> p.getThoiGianThanhToan().toLocalDate(),
//                         Collectors.reducing(
//                                 BigDecimal.ZERO, 
//                                 PaymentEntity::getSoTien, 
//                                 BigDecimal::add
//                         )
//                 ));

//         // Map sang DTO cho biểu đồ
//         List<RevenueReportDTO> revenueChart = revenueByDate.entrySet().stream()
//                 .map(entry -> RevenueReportDTO.builder()
//                         .date(entry.getKey().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
//                         .income(entry.getValue())
//                         .build())
//                 .collect(Collectors.toList());

//         // 3. Xử lý danh sách lịch khám gần đây
//         List<AppointmentScheduleEntity> recentApps = dashboardRepo.getRecentAppointments(5);
//         List<RecentAppointmentDTO> appointmentDTOs = recentApps.stream().map(app -> {
//             // Giả định tên lấy từ Account, bạn điều chỉnh lại phương thức lấy tên nhé
//             String patientName = app.getPatient().getAccount() != null ? 
//                                  "Tên Bệnh Nhân" : "Chưa cập nhật"; 
            
//             return RecentAppointmentDTO.builder()
//                     .patientName(patientName)
//                     .date(app.getNgayKham().toString())
//                     .time(app.getGioKham() != null ? app.getGioKham().toString() : "")
//                     .doctorName(app.getDoctor().getTenBacSi())
//                     .treatment(app.getLoaiKham())
//                     .status(app.getTrangThai())
//                     .build();
//         }).collect(Collectors.toList());

//         // 4. Đóng gói trả về
//         return DashboardResponseDTO.builder()
//                 .revenueLast7Days(revenueChart)
//                 .recentAppointments(appointmentDTOs)
//                 // Map thêm các thông số KPI khác tại đây
//                 .build();
//     }
    
// }
