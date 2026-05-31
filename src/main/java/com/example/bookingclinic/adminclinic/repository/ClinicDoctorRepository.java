package com.example.bookingclinic.adminclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminclinic.dto.response.DoctorSimpleResponse;
import com.example.bookingclinic.adminclinic.entity.DoctorEntity;
import com.example.bookingclinic.adminclinic.repository.custom.DoctorRepositoryCustom;

@Repository
public interface ClinicDoctorRepository extends JpaRepository<DoctorEntity, String>, DoctorRepositoryCustom {
    @Query(value = "SELECT MAX(CAST(SUBSTRING(ma_bac_si, 3, LEN(ma_bac_si)) AS INT)) FROM bac_si WHERE ma_bac_si LIKE 'BS%'", nativeQuery = true)
    Integer findMaxDoctorIdNumber();

    DoctorEntity findByAccount_MaTaiKhoan(String maTaiKhoan);

    @Query("SELECT new com.example.bookingclinic.adminclinic.dto.response.DoctorSimpleResponse(d.maBacSi, d.tenBacSi) " +
           "FROM DoctorEntity d WHERE d.clinic.maPhongKham = :maPhongKham AND d.isDeleted = false")
    List<DoctorSimpleResponse> findActiveDoctorsByClinic(@Param("maPhongKham") String maPhongKham);
}
