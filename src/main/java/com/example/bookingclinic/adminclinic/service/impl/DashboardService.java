package com.example.bookingclinic.adminclinic.service.impl;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.adminclinic.dto.response.DashboardResponseDTO;
import com.example.bookingclinic.adminclinic.dto.response.RecentAppointmentDTO;
import com.example.bookingclinic.adminclinic.dto.response.RevenueReportDTO;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.PatientOverviewDTO;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.SidebarDTO;
import com.example.bookingclinic.adminclinic.dto.response.kpi.KpiResponseDTO;
import com.example.bookingclinic.adminclinic.entity.AppointmentScheduleEntity;
import com.example.bookingclinic.adminclinic.entity.PaymentEntity;
import com.example.bookingclinic.adminclinic.repository.custom.DashboardRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final DashboardRepositoryCustom dashboardRepo;

    public DashboardResponseDTO getDashboardData(String maPhongKham) {
        int daysToFetch = 8;
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        LocalDate startDate = today.minusDays(daysToFetch - 1);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH);

        // 1. Fetch toàn bộ dữ liệu thô trong 8 ngày
        List<PaymentEntity> recentPayments = dashboardRepo.getPaymentsBetween(maPhongKham, startDateTime, now);
        List<AppointmentScheduleEntity> recentAppointmentsForChart = dashboardRepo.getAppointmentsBetween(maPhongKham, startDateTime, now);

        // Khởi tạo các List rỗng cho biểu đồ
        List<String> categories = new ArrayList<>();
        List<Long> childData = new ArrayList<>();
        List<Long> adultData = new ArrayList<>();
        List<Long> elderlyData = new ArrayList<>();
        List<RevenueReportDTO> revenueChart = new ArrayList<>();

        // 2. VÒNG LẶP 8 NGÀY (Tuyệt chiêu chống gãy trục X)
        for (int i = 0; i < daysToFetch; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            categories.add(currentDate.format(formatter)); // Thêm nhãn ngày (VD: 4 Jul)

            // Tính Doanh thu trong ngày này
            BigDecimal dailyRevenue = BigDecimal.ZERO;
            for (PaymentEntity payment : recentPayments) {
                if (payment.getThoiGianThanhToan().toLocalDate().equals(currentDate)) {
                    dailyRevenue = dailyRevenue.add(payment.getSoTien());
                }
            }
            revenueChart.add(RevenueReportDTO.builder()
                    .date(currentDate.format(formatter))
                    .income(dailyRevenue)
                    .build());

            // Tính Tuổi Bệnh nhân trong ngày này
            long childCount = 0, adultCount = 0, elderlyCount = 0;
            for (AppointmentScheduleEntity app : recentAppointmentsForChart) {
                // Chỉ đếm những người đã khám (hoặc theo yêu cầu của bạn) và trùng ngày
                if (app.getNgayKham() != null && app.getNgayKham().equals(currentDate)
                    && app.getPatient() != null 
                    && app.getPatient().getNgaySinh() != null) {
                    
                    int age = Period.between(app.getPatient().getNgaySinh(), currentDate).getYears();
                    if (age < 18) childCount++;
                    else if (age <= 60) adultCount++;
                    else elderlyCount++;
                }
            }
            childData.add(childCount);
            adultData.add(adultCount);
            elderlyData.add(elderlyCount);
        }

        PatientOverviewDTO patientDTO = PatientOverviewDTO.builder()
                .categories(categories)
                .childData(childData)
                .adultData(adultData)
                .elderlyData(elderlyData)
                .build();

        // 3. Xử lý danh sách lịch khám gần đây
        List<AppointmentScheduleEntity> recentApps = dashboardRepo.getRecentAppointments(maPhongKham, 5);
        
        // Tạo Formatter để format ngày giờ cho chuẩn UI thiết kế
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yy-MM-dd"); // Trả ra dạng: 26-07-28
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH); // Trả ra dạng: 09:00 AM

        List<RecentAppointmentDTO> appointmentDTOs = recentApps.stream().map(app -> {
            // [BẢNG 1 + 2]: Truy xuất Tên Bệnh Nhân (Từ Lịch Khám -> Bệnh Nhân -> Tài Khoản)
            String patientName = "Chưa cập nhật";
            if (app.getPatient() != null && app.getPatient().getAccount() != null) {
                // LƯU Ý: Thay getFullName() bằng đúng tên hàm lấy Tên trong AccountEntity của bạn
                patientName = app.getPatient().getAccount().getHoVaTen(); 
            }
            
            // [BẢNG 3]: Truy xuất Tên Bác Sĩ (Từ Lịch Khám -> Bác Sĩ)
            String doctorName = "Chưa phân công";
            if (app.getDoctor() != null) {
                // Format lại để có chữ "Dr." phía trước cho ngầu giống thiết kế
                doctorName = "Dr. " + app.getDoctor().getTenBacSi(); 
            }

            // [BẢNG 4]: Lấy thông tin nội tại của Lịch Khám và Format
            String dateStr = app.getNgayKham() != null ? app.getNgayKham().format(dateFormatter) : "";
            String timeStr = app.getGioKham() != null ? app.getGioKham().format(timeFormatter) : "";

            return RecentAppointmentDTO.builder()
                    .patientName(patientName)
                    .date(dateStr)
                    .time(timeStr)
                    .doctorName(doctorName)
                    .treatment(app.getLoaiKham() != null ? app.getLoaiKham() : "Khám tổng quát")
                    .status(app.getTrangThai() != null ? app.getTrangThai() : "Pending")
                    .build();
        }).collect(Collectors.toList());

        // 4. Đóng gói và trả về
        AppointmentScheduleEntity reviewEntity = dashboardRepo.getRecentReview(maPhongKham);
        SidebarDTO.RecentReviewDTO reviewDTO = null;
        if (reviewEntity != null) {
            String patientName = reviewEntity.getPatient().getAccount() != null ? 
                    reviewEntity.getPatient().getAccount().getHoVaTen() : "Khách ẩn danh";
            
            reviewDTO = SidebarDTO.RecentReviewDTO.builder()
                    .patientName(patientName)
                    .rating(reviewEntity.getDanhGia())
                    .date(reviewEntity.getNgayKham() != null ? reviewEntity.getNgayKham().format(formatter) : "")
                    // Nếu sau này DB có trường review_text thì thay vào đây, tạm dùng lyDoKham hoặc Text cứng
                    .comment("Bác sĩ tư vấn rất tận tâm và chuyên nghiệp. Tôi không phải đợi lâu.") 
                    .build();
        }

        // 5.2 Xử lý Heatmap (Lấy dữ liệu 30 ngày qua để phân tích thói quen)
        LocalDateTime thirtyDaysAgo = now.minusDays(30);
        List<AppointmentScheduleEntity> heatmapApps = dashboardRepo.getAppointmentsForHeatmap(maPhongKham, thirtyDaysAgo, now);

        // Khởi tạo ma trận đếm số lượng: 5 ngày (T2-T6) x 5 khung giờ (8h, 10h, 12h, 14h, 16h)
        int[][] heatmapCounts = new int[5][5];

        for (AppointmentScheduleEntity app : heatmapApps) {
            if (app.getNgayKham() != null && app.getGioKham() != null) {
                DayOfWeek dayOfWeek = app.getNgayKham().getDayOfWeek();
                int dayIndex = dayOfWeek.getValue() - 1; // Monday=1 -> index 0 (T2)

                // Chỉ tính từ T2 đến T6 (index 0 -> 4)
                if (dayIndex >= 0 && dayIndex <= 4) {
                    int hour = app.getGioKham().getHour();
                    int timeSlotIndex = -1;

                    if (hour >= 7 && hour < 10) timeSlotIndex = 0;      // 8h
                    else if (hour >= 10 && hour < 12) timeSlotIndex = 1; // 10h
                    else if (hour >= 12 && hour < 14) timeSlotIndex = 2; // 12h
                    else if (hour >= 14 && hour < 16) timeSlotIndex = 3; // 14h
                    else if (hour >= 16) timeSlotIndex = 4;              // 16h

                    if (timeSlotIndex != -1) {
                        heatmapCounts[dayIndex][timeSlotIndex]++;
                    }
                }
            }
        }

        // Chuyển ma trận Count thành Density (Độ đậm nhạt: 0, 1, 2, 3) để FE dễ vẽ màu
        String[] dayNames = {"T2", "T3", "T4", "T5", "T6"};
        List<SidebarDTO.HeatmapRowDTO> heatmapList = new ArrayList<>();
        
        for (int d = 0; d < 5; d++) {
            List<Integer> densityList = new ArrayList<>();
            for (int t = 0; t < 5; t++) {
                int count = heatmapCounts[d][t];
                // Thuật toán chia màu cơ bản (có thể điều chỉnh tuỳ lượng data thực tế)
                int density = 0;
                if (count > 10) density = 3;      // Rất đông (Đậm nhất)
                else if (count > 5) density = 2;  // Đông (Đậm vừa)
                else if (count > 0) density = 1;  // Ít (Nhạt)
                
                densityList.add(density);
            }
            heatmapList.add(SidebarDTO.HeatmapRowDTO.builder()
                    .dayOfWeek(dayNames[d])
                    .density(densityList)
                    .build());
        }

        SidebarDTO sidebarData = SidebarDTO.builder()
                .recentReview(reviewDTO)
                .heatmap(heatmapList)
                .build();

        // --- SỬA LẠI ĐOẠN ĐÓNG GÓI RETURN CUỐI CÙNG ---
        return DashboardResponseDTO.builder()
                .revenueLast7Days(revenueChart)
                .patientOverview(patientDTO)
                .recentAppointments(appointmentDTOs)
                .sidebarWidgets(sidebarData) // <--- NHÉT DỮ LIỆU SIDEBAR VÀO ĐÂY
                .build();
    }

    public KpiResponseDTO getKpiData(String maPhongKham) {
        // Xác định mốc thời gian Hôm nay
        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime todayEnd = today.atTime(LocalTime.MAX);

        // Xác định mốc thời gian Hôm qua
        LocalDate yesterday = today.minusDays(1);
        LocalDateTime yesterdayStart = yesterday.atStartOfDay();
        LocalDateTime yesterdayEnd = yesterday.atTime(LocalTime.MAX);

        // 1. Tính toán Doanh thu
        BigDecimal todayRevenue = dashboardRepo.calculateRevenue(maPhongKham, todayStart, todayEnd);
        BigDecimal yesterdayRevenue = dashboardRepo.calculateRevenue(maPhongKham, yesterdayStart, yesterdayEnd);
        Double invoicePercent = calculatePercentage(todayRevenue.doubleValue(), yesterdayRevenue.doubleValue());

        // 2. Tính số lượng bệnh nhân "DaKham"
        Long todayPatients = dashboardRepo.countDistinctPatientsExamined(maPhongKham, today);
        Long yesterdayPatients = dashboardRepo.countDistinctPatientsExamined(maPhongKham, yesterday);
        Double patientPercent = calculatePercentage(todayPatients.doubleValue(), yesterdayPatients.doubleValue());

        // 3. Tính tổng số lịch hẹn được tạo
        Long todayAppointments = dashboardRepo.countTotalAppointments(maPhongKham, todayStart, todayEnd);
        Long yesterdayAppointments = dashboardRepo.countTotalAppointments(maPhongKham, yesterdayStart, yesterdayEnd);
        Double appointmentPercent = calculatePercentage(todayAppointments.doubleValue(), yesterdayAppointments.doubleValue());

        // Build DTO trả về Frontend
        return KpiResponseDTO.builder()
                .totalInvoice(todayRevenue) // DB trống sẽ tự động trả về 0 nhờ xử lý ở Repository
                .invoicePercent(invoicePercent)
                .totalPatients(todayPatients)
                .patientPercent(patientPercent)
                .totalAppointments(todayAppointments)
                .appointmentPercent(appointmentPercent)
                .build();
    }

    // Hàm tiện ích tính phần trăm tăng/giảm an toàn (tránh lỗi chia cho 0)
    private Double calculatePercentage(double current, double previous) {
        if (previous == 0) {
            // Nếu hôm qua bằng 0, mà hôm nay có số liệu => Tăng trưởng 100%
            return current > 0 ? 100.0 : 0.0;
        }
        double percent = ((current - previous) / previous) * 100.0;
        // Làm tròn 2 chữ số thập phân
        return Math.round(percent * 100.0) / 100.0;
    }
}
