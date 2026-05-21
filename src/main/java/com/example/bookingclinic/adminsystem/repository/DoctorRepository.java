package com.example.bookingclinic.adminsystem.repository;

import com.example.bookingclinic.adminsystem.entity.DoctorEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, String> {
    Optional<DoctorEntity> findByEmail(String email);

    Optional<DoctorEntity> findBySoDienThoai(String soDienThoai);

    boolean existsByEmail(String email);

    boolean existsBySoDienThoai(String soDienThoai);

    DoctorEntity findByAccount_MaTaiKhoan(String maTaiKhoan);

}
