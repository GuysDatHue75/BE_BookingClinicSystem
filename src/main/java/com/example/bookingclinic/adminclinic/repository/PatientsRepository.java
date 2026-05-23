package com.example.bookingclinic.adminclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookingclinic.adminclinic.entity.AccountEntity;
import com.example.bookingclinic.adminclinic.entity.PatientsEntity;

public interface PatientsRepository extends JpaRepository<PatientsEntity, String>{
    PatientsEntity findByEmail(String email);
    PatientsEntity findByAccount_MaTaiKhoan(String id);
    PatientsEntity findByAccount(AccountEntity account);
}
