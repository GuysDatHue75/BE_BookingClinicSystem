package com.example.bookingclinic.adminclinic.repository.custom;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.bookingclinic.adminclinic.entity.AppointmentScheduleEntity;
import com.example.bookingclinic.adminclinic.entity.PaymentEntity;

public interface DashboardRepositoryCustom {
    List<PaymentEntity> getPaymentsBetween(String maPhongKham, LocalDateTime startDate, LocalDateTime endDate);
    List<AppointmentScheduleEntity> getAppointmentsBetween(String maPhongKham, LocalDateTime startDate, LocalDateTime endDate);
    List<AppointmentScheduleEntity> getRecentAppointments(String maPhongKham, int limit);
    BigDecimal calculateRevenue(String maPhongKham, LocalDateTime start, LocalDateTime end);
    Long countDistinctPatientsExamined(String maPhongKham, LocalDate targetDate);
    Long countTotalAppointments(String maPhongKham, LocalDateTime start, LocalDateTime end);
    List<AppointmentScheduleEntity> getAppointmentsForHeatmap(String maPhongKham, LocalDateTime startDate, LocalDateTime endDate);
    AppointmentScheduleEntity getRecentReview(String maPhongKham);
}
