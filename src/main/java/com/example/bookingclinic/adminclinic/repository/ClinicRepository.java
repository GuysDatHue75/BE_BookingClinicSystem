package com.example.bookingclinic.adminclinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminclinic.entity.ClinicEntity;

@Repository
public interface ClinicRepository extends JpaRepository<ClinicEntity, String> {
    Optional<ClinicEntity> findByAccount_MaTaiKhoan(String maTaiKhoan);
}
