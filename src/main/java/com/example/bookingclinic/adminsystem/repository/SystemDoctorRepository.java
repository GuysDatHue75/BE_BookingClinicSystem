package com.example.bookingclinic.adminsystem.repository;

import com.example.bookingclinic.adminclinic.entity.DoctorEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemDoctorRepository extends JpaRepository<DoctorEntity, String> {
    Optional<DoctorEntity> findByEmail(String email);

    Optional<DoctorEntity> findBySoDienThoai(String soDienThoai);

    boolean existsByEmail(String email);

    boolean existsBySoDienThoai(String soDienThoai);

    DoctorEntity findByAccount_MaTaiKhoan(String maTaiKhoan);

}
