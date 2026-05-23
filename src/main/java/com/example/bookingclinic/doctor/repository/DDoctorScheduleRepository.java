package com.example.bookingclinic.doctor.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminclinic.entity.DoctorScheduleEntity;


@Repository

public interface DDoctorScheduleRepository extends JpaRepository<DoctorScheduleEntity, String> {
    // SELECT COUNT(*) > 0 FROM lich_lam_viec WHERE ma_bac_si = ? AND ngay_lam_viec
    // = ? AND khung_gio = ?
    boolean existsByDoctor_MaBacSiAndNgayLamViecAndTimeslot_MaKhungGio(String maBacSi,LocalDate ngayLamViec,String maKhungGio);

    // Lấy danh sách lịch làm việc
    // SELECT *
    // FROM BACSI B JOIN LICHLAMVIEC LLV ON B.MABACSI = LLV.MABACSI
    // LEFT JOIN LICHKHAM LK ON LLV.MALICHLAM = LK.MALICHKHAM
    // ORERBY LLV.NGAYLAMVIEC DESC

    // Lấy lịch trong tuần của bác sĩ
    List<DoctorScheduleEntity> findByDoctor_MaBacSiAndNgayLamViecBetweenOrderByNgayLamViecAscTimeslot_KhungGioBatDauAsc(
            String maBacSi,
            LocalDate startDate,
            LocalDate endDate);

}
