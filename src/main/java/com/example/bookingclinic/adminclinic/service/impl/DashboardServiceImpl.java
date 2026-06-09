package com.example.bookingclinic.adminclinic.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.adminclinic.dto.response.DashboardResponseDTO;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.AgeGroupChartResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.AppointmentDashboardResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.DashboardKpiResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.HeatmapResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.RecentReviewResponse;
import com.example.bookingclinic.adminclinic.dto.response.dashboard.RevenueChartResponse;
import com.example.bookingclinic.adminclinic.entity.AppointmentScheduleEntity;
import com.example.bookingclinic.adminclinic.entity.PaymentEntity;
import com.example.bookingclinic.adminclinic.repository.ClinicDoctorRepository;
import com.example.bookingclinic.adminclinic.repository.ClinicRepository;
import com.example.bookingclinic.adminclinic.repository.custom.DashboardRepositoryCustom;
import com.example.bookingclinic.adminclinic.service.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepositoryCustom dashboardRepo;
    private final ClinicDoctorRepository    doctorRepo;
    private final ClinicRepository          clinicRepo;

    @Override
    public DashboardResponseDTO getFullDashboard(String maPhongKham) {
        return DashboardResponseDTO.builder()
                .kpi(getKpi(maPhongKham))
                .ageChart(getAgeGroupChart(maPhongKham, 7))
                .revenueChart(getRevenueChart(maPhongKham, RevenueChartResponse.Mode.WEEK))
                .heatmap(getHeatmap(maPhongKham, 30))
                .recentAppointments(getRecentAppointments(maPhongKham, 10))
                .recentReview(getRecentReview(maPhongKham))
                .build();
    }

    @Override
    public DashboardKpiResponse getKpi(String maPhongKham) {
        LocalDate today     = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        BigDecimal revToday     = dashboardRepo.calculateRevenue(maPhongKham, startOf(today),     endOf(today));
        BigDecimal revYesterday = dashboardRepo.calculateRevenue(maPhongKham, startOf(yesterday), endOf(yesterday));

        Long apptToday     = dashboardRepo.countTotalAppointments(maPhongKham, startOf(today),     endOf(today));
        Long apptYesterday = dashboardRepo.countTotalAppointments(maPhongKham, startOf(yesterday), endOf(yesterday));

        Long patToday     = dashboardRepo.countDistinctPatientsExamined(maPhongKham, today);
        Long patYesterday = dashboardRepo.countDistinctPatientsExamined(maPhongKham, yesterday);

        long activeDoctors = doctorRepo.findActiveDoctorsByClinic(maPhongKham).size();

        return DashboardKpiResponse.builder()
                .doanhThuHomNay(revToday)
                .doanhThuHomQua(revYesterday)
                .phanTramDoanhThu(percentChange(revYesterday, revToday))
                .lichHenHomNay(apptToday)
                .lichHenHomQua(apptYesterday)
                .phanTramLichHen(percentChange(apptYesterday, apptToday))
                .benhNhanHomNay(patToday)
                .benhNhanHomQua(patYesterday)
                .phanTramBenhNhan(percentChange(patYesterday, patToday))
                .soLuongBacSiHoatDong(activeDoctors)
                .build();
    }

    @Override
    public RevenueChartResponse getRevenueChart(String maPhongKham, RevenueChartResponse.Mode mode) {
        LocalDate today = LocalDate.now();

        return switch (mode) {
            case WEEK  -> buildWeeklyRevenue(maPhongKham, today);
            case MONTH -> buildMonthlyRevenue(maPhongKham, today);
            case YEAR  -> buildYearlyRevenue(maPhongKham, today);
        };
    }

    private RevenueChartResponse buildWeeklyRevenue(String maPhongKham, LocalDate today) {
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek   = startOfWeek.plusDays(6);

        List<PaymentEntity> payments = dashboardRepo.getPaymentsBetween(
                maPhongKham, startOf(startOfWeek), endOf(endOfWeek));

        Map<LocalDate, BigDecimal> revenueMap = buildRevenueByDate(payments);

        List<String>     labels  = new ArrayList<>();
        List<BigDecimal> income  = new ArrayList<>();
        List<BigDecimal> expense = new ArrayList<>();

        String[] dayNames = {"T2", "T3", "T4", "T5", "T6", "T7", "CN"};
        for (int i = 0; i < 7; i++) {
            LocalDate d = startOfWeek.plusDays(i);
            BigDecimal rev = revenueMap.getOrDefault(d, BigDecimal.ZERO);
            labels.add(dayNames[i]);
            income.add(rev);
            expense.add(estimateExpense(rev));
        }

        return RevenueChartResponse.builder()
                .mode(RevenueChartResponse.Mode.WEEK)
                .labels(labels).income(income).expense(expense)
                .build();
    }

    private RevenueChartResponse buildMonthlyRevenue(String maPhongKham, LocalDate today) {
        List<String>     labels  = new ArrayList<>();
        List<BigDecimal> income  = new ArrayList<>();
        List<BigDecimal> expense = new ArrayList<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/yyyy");
        for (int i = 5; i >= 0; i--) {
            LocalDate first = today.minusMonths(i).withDayOfMonth(1);
            LocalDate last  = first.with(TemporalAdjusters.lastDayOfMonth());
            BigDecimal rev  = dashboardRepo.calculateRevenue(maPhongKham, startOf(first), endOf(last));
            labels.add(first.format(fmt));
            income.add(rev);
            expense.add(estimateExpense(rev));
        }

        return RevenueChartResponse.builder()
                .mode(RevenueChartResponse.Mode.MONTH)
                .labels(labels).income(income).expense(expense)
                .build();
    }

    private RevenueChartResponse buildYearlyRevenue(String maPhongKham, LocalDate today) {
        List<String>     labels  = new ArrayList<>();
        List<BigDecimal> income  = new ArrayList<>();
        List<BigDecimal> expense = new ArrayList<>();

        int year = today.getYear();
        int[][] quarters = {{1,3},{4,6},{7,9},{10,12}};
        String[] qLabels = {"Q1","Q2","Q3","Q4"};

        for (int q = 0; q < 4; q++) {
            LocalDate start = LocalDate.of(year, quarters[q][0], 1);
            LocalDate end   = LocalDate.of(year, quarters[q][1], 1)
                                       .with(TemporalAdjusters.lastDayOfMonth());
            BigDecimal rev  = dashboardRepo.calculateRevenue(maPhongKham, startOf(start), endOf(end));
            labels.add(qLabels[q]);
            income.add(rev);
            expense.add(estimateExpense(rev));
        }

        return RevenueChartResponse.builder()
                .mode(RevenueChartResponse.Mode.YEAR)
                .labels(labels).income(income).expense(expense)
                .build();
    }

    @Override
    public AgeGroupChartResponse getAgeGroupChart(String maPhongKham, int days) {
        LocalDate today = LocalDate.now();
        LocalDate from  = today.minusDays(days - 1);

        List<AppointmentScheduleEntity> appts = dashboardRepo.getAppointmentsBetween(
                maPhongKham, startOf(from), endOf(today));

        if (days <= 30) {
            return buildDailyAgeChart(appts, from, today);
        } else {
            return buildWeeklyAgeChart(appts, from, today);
        }
    }

    private AgeGroupChartResponse buildDailyAgeChart(List<AppointmentScheduleEntity> appts, LocalDate from, LocalDate to) {
        Map<LocalDate, long[]> map = new LinkedHashMap<>();
        for (LocalDate d = from; !d.isAfter(to); d = d.plusDays(1)) {
            map.put(d, new long[3]);
        }

        categorizeByAge(appts, map, appt -> appt.getNgayKham());

        List<String> labels   = new ArrayList<>();
        List<Long>   treEm    = new ArrayList<>();
        List<Long>   nguoiLon = new ArrayList<>();
        List<Long>   caoTuoi  = new ArrayList<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d/M");
        map.forEach((d, v) -> {
            labels.add(d.format(fmt));
            treEm.add(v[0]);
            nguoiLon.add(v[1]);
            caoTuoi.add(v[2]);
        });

        return buildAgeResponse(labels, treEm, nguoiLon, caoTuoi);
    }

    private AgeGroupChartResponse buildWeeklyAgeChart(List<AppointmentScheduleEntity> appts, LocalDate from, LocalDate to) {
        Map<Integer, long[]> map = new LinkedHashMap<>();
        int totalWeeks = (int) Math.ceil((to.toEpochDay() - from.toEpochDay() + 1) / 7.0);
        for (int i = 0; i < totalWeeks; i++) map.put(i, new long[3]);

        for (AppointmentScheduleEntity appt : appts) {
            if (appt.getPatient() == null || appt.getPatient().getNgaySinh() == null) continue;
            int weekIdx = (int)((appt.getNgayKham().toEpochDay() - from.toEpochDay()) / 7);
            if (weekIdx < 0 || weekIdx >= totalWeeks) continue;
            int age = ageOf(appt.getPatient().getNgaySinh());
            map.get(weekIdx)[ageGroup(age)]++;
        }

        List<String> labels   = new ArrayList<>();
        List<Long>   treEm    = new ArrayList<>();
        List<Long>   nguoiLon = new ArrayList<>();
        List<Long>   caoTuoi  = new ArrayList<>();

        map.forEach((w, v) -> {
            labels.add("T" + (w + 1));
            treEm.add(v[0]);
            nguoiLon.add(v[1]);
            caoTuoi.add(v[2]);
        });

        return buildAgeResponse(labels, treEm, nguoiLon, caoTuoi);
    }

    @FunctionalInterface
    private interface DateExtractor {
        LocalDate extract(AppointmentScheduleEntity a);
    }

    private void categorizeByAge(
            List<AppointmentScheduleEntity> appts,
            Map<LocalDate, long[]> map,
            DateExtractor extractor) {

        for (AppointmentScheduleEntity appt : appts) {
            if (appt.getPatient() == null || appt.getPatient().getNgaySinh() == null) continue;
            LocalDate key = extractor.extract(appt);
            long[]    bucket = map.get(key);
            if (bucket == null) continue;
            int age = ageOf(appt.getPatient().getNgaySinh());
            bucket[ageGroup(age)]++;
        }
    }

    private AgeGroupChartResponse buildAgeResponse(
            List<String> labels, List<Long> treEm, List<Long> nguoiLon, List<Long> caoTuoi) {
        return AgeGroupChartResponse.builder()
                .labels(labels)
                .treEm(treEm)
                .nguoiLon(nguoiLon)
                .caoTuoi(caoTuoi)
                .tongTreEm(treEm.stream().mapToLong(Long::longValue).sum())
                .tongNguoiLon(nguoiLon.stream().mapToLong(Long::longValue).sum())
                .tongCaoTuoi(caoTuoi.stream().mapToLong(Long::longValue).sum())
                .build();
    }

    @Override
    public HeatmapResponse getHeatmap(String maPhongKham, int days) {
        LocalDate today = LocalDate.now();
        LocalDate from  = today.minusDays(days - 1);

        List<AppointmentScheduleEntity> appts = dashboardRepo.getAppointmentsForHeatmap(
                maPhongKham, startOf(from), endOf(today));

        int numDays  = 7;
        int numHours = 11; 
        long[][] matrix = new long[numDays][numHours];

        for (AppointmentScheduleEntity a : appts) {
            if (a.getGioKham() == null) continue;
            int hour = a.getGioKham().getHour();
            if (hour < 8 || hour > 18) continue;

            int dayIdx  = a.getNgayKham().getDayOfWeek().getValue() - 1;
            int hourIdx = hour - 8;
            matrix[dayIdx][hourIdx]++;
        }

        List<List<Long>> data = new ArrayList<>();
        long maxVal = 0;
        for (long[] row : matrix) {
            List<Long> r = new ArrayList<>();
            for (long v : row) { r.add(v); maxVal = Math.max(maxVal, v); }
            data.add(r);
        }

        return HeatmapResponse.builder()
                .dayLabels(Arrays.asList("T2","T3","T4","T5","T6","T7","CN"))
                .hourLabels(List.of(8,9,10,11,12,13,14,15,16,17,18))
                .data(data)
                .maxValue(maxVal)
                .build();
    }

    @Override
    public List<AppointmentDashboardResponse> getRecentAppointments(String maPhongKham, int limit) {
        return dashboardRepo.getRecentAppointments(maPhongKham, limit)
                .stream()
                .map(this::toAppointmentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDashboardResponse> getAppointmentsByDate(String maPhongKham, LocalDate date) {
        List<AppointmentScheduleEntity> all = dashboardRepo
                .getAppointmentsBetween(maPhongKham, startOf(date), endOf(date));
        return all.stream()
                .map(this::toAppointmentResponse)
                .sorted((a, b) -> {
                    if (a.getGioKham() == null) return 1;
                    if (b.getGioKham() == null) return -1;
                    return a.getGioKham().compareTo(b.getGioKham());
                })
                .collect(Collectors.toList());
    }

    private AppointmentDashboardResponse toAppointmentResponse(AppointmentScheduleEntity a) {
        String tenBenhNhan = (a.getPatient() != null && a.getPatient().getAccount() != null)
                ? a.getPatient().getAccount().getHoVaTen()
                : "—";
        String tenBacSi = (a.getDoctor() != null)
                ? a.getDoctor().getTenBacSi()
                : "—";

        return AppointmentDashboardResponse.builder()
                .maLichKham(a.getMaLichKham())
                .maBenhNhan(a.getPatient() != null ? a.getPatient().getMaBenhNhan() : null)
                .tenBenhNhan(tenBenhNhan)
                .maBacSi(a.getDoctor() != null ? a.getDoctor().getMaBacSi() : null)
                .tenBacSi(tenBacSi)
                .ngayKham(a.getNgayKham())
                .gioKham(a.getGioKham())
                .loaiKham(a.getLoaiKham())
                .trangThai(a.getTrangThai())
                .danhGia(a.getDanhGia())
                .build();
    }

    @Override
    public RecentReviewResponse getRecentReview(String maPhongKham) {
        AppointmentScheduleEntity a = dashboardRepo.getRecentReview(maPhongKham);
        if (a == null) return null;

        String tenBenhNhan = (a.getPatient() != null && a.getPatient().getAccount() != null)
                ? a.getPatient().getAccount().getHoVaTen()
                : "—";
        String tenBacSi = (a.getDoctor() != null) ? a.getDoctor().getTenBacSi() : "—";

        return RecentReviewResponse.builder()
                .maLichKham(a.getMaLichKham())
                .tenBenhNhan(tenBenhNhan)
                .danhGia(a.getDanhGia())
                .ngayKham(a.getNgayKham())
                .tenBacSi(tenBacSi)
                .build();
    }

    private LocalDateTime startOf(LocalDate d) {
        return d.atStartOfDay();
    }

    private LocalDateTime endOf(LocalDate d) {
        return d.atTime(LocalTime.MAX);
    }

    private double percentChange(BigDecimal prev, BigDecimal curr) {
        if (prev == null || prev.compareTo(BigDecimal.ZERO) == 0) return 0.0;
        return curr.subtract(prev)
                   .divide(prev, 4, RoundingMode.HALF_UP)
                   .multiply(BigDecimal.valueOf(100))
                   .setScale(2, RoundingMode.HALF_UP)
                   .doubleValue();
    }

    private double percentChange(long prev, long curr) {
        if (prev == 0) return 0.0;
        return Math.round(((double)(curr - prev) / prev) * 10000.0) / 100.0;
    }

    private BigDecimal estimateExpense(BigDecimal revenue) {
        if (revenue == null) return BigDecimal.ZERO;
        return revenue.multiply(BigDecimal.valueOf(0.7)).setScale(2, RoundingMode.HALF_UP);
    }

    private int ageOf(LocalDate dob) {
        return LocalDate.now().getYear() - dob.getYear();
    }

    private int ageGroup(int age) {
        if (age < 18) return 0;
        if (age <= 60) return 1;
        return 2;
    }

    private Map<LocalDate, BigDecimal> buildRevenueByDate(List<PaymentEntity> payments) {
        Map<LocalDate, BigDecimal> map = new LinkedHashMap<>();
        for (PaymentEntity p : payments) {
            if (p.getThoiGianThanhToan() == null) continue;
            LocalDate d = p.getThoiGianThanhToan().toLocalDate();
            map.merge(d, p.getSoTien() != null ? p.getSoTien() : BigDecimal.ZERO, BigDecimal::add);
        }
        return map;
    }
}