package com.example.bookingclinic.doctor.repository.ScheduleRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.doctor.dto.schedule.ScheduleResponseDTO;
import com.example.bookingclinic.doctor.entity.Schedule.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    @Query("SELECT new com.example.bookingclinic.doctor.dto.schedule.ScheduleResponseDTO(" +
            "a.maLichKham, acc.hoVaTen, p.ngaySinh, p.gioiTinh, acc.soDt, " +
            "p.diaChi, a.lyDoKham, a.trangThai, ds.ngayLamViec, ds.khungGioKham.maKhungGio) " + 
            "FROM Appointment a " +
            "JOIN a.benhNhan p " +
            "JOIN p.taiKhoan acc " +
            "JOIN a.lichLamViec ds " +
            "WHERE ds.bacSi.maBacSi = :maBacSi " +
            "AND a.trangThai = 'ChoXacNhan'")
    List<ScheduleResponseDTO> getDSChuaXacNhan(@Param("maBacSi") String maBacSi);

    @Query("SELECT new com.example.bookingclinic.doctor.dto.schedule.ScheduleResponseDTO(" +
            "a.maLichKham, acc.hoVaTen, p.ngaySinh, p.gioiTinh, acc.soDt, " +
            "p.diaChi, a.lyDoKham, a.trangThai, ds.ngayLamViec, ds.khungGioKham.maKhungGio) " +
            "FROM Appointment a " +
            "JOIN a.benhNhan p " +
            "JOIN p.taiKhoan acc " +
            "JOIN a.lichLamViec ds " +
            "WHERE ds.bacSi.maBacSi = :maBacSi " +
            "AND a.trangThai = 'DaXacNhan'")
    List<ScheduleResponseDTO> getDSDaXacNhan(@Param("maBacSi") String maBacSi);
}