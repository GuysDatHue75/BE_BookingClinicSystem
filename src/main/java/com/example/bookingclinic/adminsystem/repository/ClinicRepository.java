package com.example.bookingclinic.adminsystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminsystem.entity.ClinicEntity;
import com.example.bookingclinic.adminsystem.repository.custom.ClinicRepositoryCustom;

@Repository
public interface ClinicRepository extends JpaRepository<ClinicEntity, String>, ClinicRepositoryCustom {

    List<ClinicEntity> findByIsDeletedFalse();

    List<ClinicEntity> findByNgayHetHanBeforeAndIsDeletedFalse(LocalDateTime time);

}
