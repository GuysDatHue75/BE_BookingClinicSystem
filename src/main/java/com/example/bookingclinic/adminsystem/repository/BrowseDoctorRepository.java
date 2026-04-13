package com.example.bookingclinic.adminsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.bookingclinic.adminsystem.entity.BrowseDoctorEntity;
import com.example.bookingclinic.adminsystem.repository.custom.BrowseDoctorRepositoryCustom;

@Repository
public interface BrowseDoctorRepository extends JpaRepository<BrowseDoctorEntity, String>, BrowseDoctorRepositoryCustom {
    //basic
    Optional<BrowseDoctorEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsBySoDienThoai(String soDienThoai);

}
