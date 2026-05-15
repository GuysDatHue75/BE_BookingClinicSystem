package com.example.bookingclinic.doctor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.doctor.dto.ConfirmAppointment.AppointmentResponseDTO;
import com.example.bookingclinic.doctor.entity.ConfirmAppointment;

@Repository
public interface ConfirmAppointmentRepository extends JpaRepository<ConfirmAppointment, String> {

    @Query("SELECT new com.example.bookingclinic.doctor.dto.ConfirmAppointment.AppointmentResponseDTO(" +
            "a.maLichKham, acc.hoVaTen, acc.ngaySinh, acc.gioiTinh, acc.soDienThoai, " +
            "acc.diaChi, a.lyDoKham, a.trangThai, ds.ngayLamViec, ds.khungGio) " +
            "FROM ConfirmAppointment a " +
            "JOIN a.taiKhoan acc " +
            "JOIN a.lichLamViec ds " +
            "WHERE ds.bacSi.maBacSi = :maBacSi " +
            "AND a.trangThai = 'ChoDuyet'")
    List<AppointmentResponseDTO> getDSChuaXacNhan(@Param("maBacSi") String maBacSi);
}