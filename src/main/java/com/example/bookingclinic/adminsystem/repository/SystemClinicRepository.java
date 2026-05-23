package com.example.bookingclinic.adminsystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminclinic.entity.ClinicEntity;
import com.example.bookingclinic.adminsystem.repository.custom.ClinicRepositoryCustom;

@Repository
public interface SystemClinicRepository extends JpaRepository<ClinicEntity, String>, ClinicRepositoryCustom {

    Page<ClinicEntity> findByIsDeletedFalse(Pageable pageable);

    List<ClinicEntity> findByNgayHetHanBeforeAndIsDeletedFalse(LocalDateTime time);

    ClinicEntity findByAccount_MaTaiKhoan(String maTaiKhoan);

}
