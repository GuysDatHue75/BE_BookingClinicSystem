package com.example.bookingclinic.adminclinic.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminclinic.dto.response.DoctorWeeklyScheduleResponse;
import com.example.bookingclinic.adminclinic.entity.DoctorScheduleEntity;

@Repository
public interface ClinicDoctorScheduleRepository
        extends JpaRepository<DoctorScheduleEntity, String>, QuerydslPredicateExecutor<DoctorScheduleEntity> {
    boolean existsByDoctor_MaBacSiAndNgayLamViecAndTimeslot_CaLamViec_MaCaLamViec(
            String maBacSi,
            LocalDate ngayLamViec,
            String maCaLamViec);

    List<DoctorScheduleEntity> findByClinic_MaPhongKhamAndNgayLamViecBetween(String maPhongKham, LocalDate tuNgay,
            LocalDate denNgay);

    List<DoctorScheduleEntity> findByClinic_MaPhongKhamAndNgayLamViecAndTimeslot_CaLamViec_MaCaLamViec(
            String maPhongKham, LocalDate ngayLamViec, String maCaLamViec);

    @Query("""
                SELECT new com.example.bookingclinic.adminclinic.dto.response.DoctorWeeklyScheduleResponse(
                    ds.maLichLamViec, ds.ngayLamViec, t.khungGioBatDau, ds.trangThai, app.maLichKham, acc.hoVaTen, app.trangThai
                )
                FROM DoctorScheduleEntity ds
                LEFT JOIN ds.timeslot t
                LEFT JOIN AppointmentScheduleEntity app ON app.doctorSchedule = ds
                LEFT JOIN app.patient p
                LEFT JOIN p.account acc
                WHERE ds.doctor.maBacSi = :maBacSi
                    AND ds.clinic.maPhongKham = :maPhongKham
                    AND ds.ngayLamViec BETWEEN :startDate AND :endDate
                ORDER BY ds.ngayLamViec ASC, t.khungGioBatDau ASC
            """)
    List<DoctorWeeklyScheduleResponse> getWeeklySchedule(
            @Param("maBacSi") String maBacSi,
            @Param("maPhongKham") String maPhongKham,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}