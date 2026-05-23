package com.example.bookingclinic.user.repository;

import com.example.bookingclinic.user.entity.Calendar;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, String> {
        List<Calendar> findByPatient_MaBenhNhanAndTrangThaiOrderByNgayKhamDesc(String id, String status);

        @Query("""
                        select l from Calendar l
                        where l.trangThai = 'DaXacNhan'
                        and l.ngayKham = :ngay
                        and (l.daGuiThongBao = 0 or l.daGuiThongBao is null)
                        """)
        List<Calendar> findLichKhamCanThongBao(@Param("ngay") LocalDate ngay);

        int countByPatient_MaBenhNhanAndTrangThai(String id, String status);

        Integer countByPatient_MaBenhNhanAndBacSi_PhongKham_MaPhongKhamAndTrangThaiIn(
                        String maBenhNhan,
                        String maPhongKham,
                        List<String> trangThai);

        List<Calendar> findByBacSi_MaBacSiAndTrangThaiAndNgayKham(String maBacSi, String trangThai, LocalDate ngayKham);

}
