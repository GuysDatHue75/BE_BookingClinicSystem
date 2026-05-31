package com.example.bookingclinic.adminclinic.repository.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminclinic.entity.AppointmentScheduleEntity;
import com.example.bookingclinic.adminclinic.entity.PaymentEntity;
import com.example.bookingclinic.adminclinic.entity.QAppointmentScheduleEntity;
import com.example.bookingclinic.adminclinic.entity.QDoctorEntity;
import com.example.bookingclinic.adminclinic.entity.QPatientsEntity;
import com.example.bookingclinic.adminclinic.entity.QPaymentEntity;
import com.example.bookingclinic.adminclinic.repository.custom.DashboardRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DashboardRepositoryCustomImpl implements DashboardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<PaymentEntity> getPaymentsBetween(String maPhongKham, LocalDateTime startDate, LocalDateTime endDate) {
        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        QAppointmentScheduleEntity appointment = QAppointmentScheduleEntity.appointmentScheduleEntity;
        QDoctorEntity doctor = QDoctorEntity.doctorEntity;
        
        return queryFactory.selectFrom(payment)
                .join(payment.lichKham, appointment)
                .join(appointment.doctor, doctor)
                .where(doctor.clinic.maPhongKham.eq(maPhongKham) // Lọc theo phòng khám
                        .and(payment.thoiGianThanhToan.between(startDate, endDate))
                        .and(payment.trangThaiThanhToan.eq("SUCCESS")))
                .fetch();
    }

    @Override
    public List<AppointmentScheduleEntity> getAppointmentsBetween(String maPhongKham, LocalDateTime startDate, LocalDateTime endDate) {
        QAppointmentScheduleEntity appointment = QAppointmentScheduleEntity.appointmentScheduleEntity;
        QDoctorEntity doctor = QDoctorEntity.doctorEntity;
        QPatientsEntity patient = QPatientsEntity.patientsEntity;

        return queryFactory.selectFrom(appointment)
                .join(appointment.doctor, doctor)
                .join(appointment.patient, patient).fetchJoin() // Lấy luôn thông tin bệnh nhân để tính tuổi
                .where(doctor.clinic.maPhongKham.eq(maPhongKham)
                        .and(appointment.ngayTao.between(startDate, endDate)))
                .fetch();
    }

    @Override
    public List<AppointmentScheduleEntity> getRecentAppointments(String maPhongKham, int limit) {
        QAppointmentScheduleEntity appointment = QAppointmentScheduleEntity.appointmentScheduleEntity;
        QDoctorEntity doctor = QDoctorEntity.doctorEntity;
        QPatientsEntity patient = QPatientsEntity.patientsEntity;
        
        return queryFactory.selectFrom(appointment)
                .join(appointment.doctor, doctor)
                .join(appointment.patient, patient).fetchJoin()
                .where(doctor.clinic.maPhongKham.eq(maPhongKham)) // Lọc theo phòng khám
                .orderBy(appointment.ngayTao.desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public BigDecimal calculateRevenue(String maPhongKham, LocalDateTime start, LocalDateTime end) {
        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        QAppointmentScheduleEntity appointment = QAppointmentScheduleEntity.appointmentScheduleEntity;
        QDoctorEntity doctor = QDoctorEntity.doctorEntity;

        BigDecimal total = queryFactory.select(payment.soTien.sum())
                .from(payment)
                .join(payment.lichKham, appointment)
                .join(appointment.doctor, doctor)
                .where(doctor.clinic.maPhongKham.eq(maPhongKham)
                        .and(payment.trangThaiThanhToan.eq("SUCCESS")) // Giả sử điều kiện thành công
                        .and(payment.thoiGianThanhToan.between(start, end)))
                .fetchOne();

        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public Long countDistinctPatientsExamined(String maPhongKham, LocalDate targetDate) {
        QAppointmentScheduleEntity appointment = QAppointmentScheduleEntity.appointmentScheduleEntity;
        QDoctorEntity doctor = QDoctorEntity.doctorEntity;

        Long count = queryFactory.select(appointment.patient.maBenhNhan.countDistinct())
                .from(appointment)
                .join(appointment.doctor, doctor)
                .where(doctor.clinic.maPhongKham.eq(maPhongKham)
                        .and(appointment.trangThai.eq("DaKham"))
                        // Lấy bệnh nhân đã khám theo ngày khám
                        .and(appointment.ngayKham.eq(targetDate))) 
                .fetchOne();

        return count != null ? count : 0L;
    }

    @Override
    public Long countTotalAppointments(String maPhongKham, LocalDateTime start, LocalDateTime end) {
        QAppointmentScheduleEntity appointment = QAppointmentScheduleEntity.appointmentScheduleEntity;
        QDoctorEntity doctor = QDoctorEntity.doctorEntity;

        Long count = queryFactory.select(appointment.count())
                .from(appointment)
                .join(appointment.doctor, doctor)
                .where(doctor.clinic.maPhongKham.eq(maPhongKham)
                        // Lọc số lịch khám được tạo trong khoảng thời gian
                        .and(appointment.ngayTao.between(start, end)))
                .fetchOne();

        return count != null ? count : 0L;
    }

    @Override
    public List<AppointmentScheduleEntity> getAppointmentsForHeatmap(String maPhongKham, LocalDateTime startDate, LocalDateTime endDate) {
        QAppointmentScheduleEntity appointment = QAppointmentScheduleEntity.appointmentScheduleEntity;
        QDoctorEntity doctor = QDoctorEntity.doctorEntity;

        return queryFactory.selectFrom(appointment)
                .join(appointment.doctor, doctor)
                .where(doctor.clinic.maPhongKham.eq(maPhongKham)
                        .and(appointment.ngayTao.between(startDate, endDate)))
                .fetch();
    }

    @Override
    public AppointmentScheduleEntity getRecentReview(String maPhongKham) {
        QAppointmentScheduleEntity appointment = QAppointmentScheduleEntity.appointmentScheduleEntity;
        QDoctorEntity doctor = QDoctorEntity.doctorEntity;
        QPatientsEntity patient = QPatientsEntity.patientsEntity;

        return queryFactory.selectFrom(appointment)
                .join(appointment.doctor, doctor)
                .join(appointment.patient, patient).fetchJoin()
                .where(doctor.clinic.maPhongKham.eq(maPhongKham)
                        .and(appointment.danhGia.isNotNull())) // Chỉ lấy những lịch có đánh giá
                .orderBy(appointment.ngayTao.desc())
                .limit(1) // Lấy 1 cái mới nhất
                .fetchOne();
    }
}
