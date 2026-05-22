package com.example.bookingclinic.adminclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminclinic.entity.DoctorEntity;
import com.example.bookingclinic.adminclinic.repository.custom.DoctorRepositoryCustom;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, String>, DoctorRepositoryCustom {
    @Query(value = "SELECT MAX(CAST(SUBSTRING(ma_bac_si, 3, LEN(ma_bac_si)) AS INT)) FROM bac_si WHERE ma_bac_si LIKE 'BS%'", nativeQuery = true)
    Integer findMaxDoctorIdNumber();

    DoctorEntity findByAccount_MaTaiKhoan(String maTaiKhoan);
}
