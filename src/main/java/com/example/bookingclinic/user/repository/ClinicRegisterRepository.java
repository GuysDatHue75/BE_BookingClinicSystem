package com.example.bookingclinic.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.user.entity.ClinicRegister;

@Repository
public interface ClinicRegisterRepository extends JpaRepository<ClinicRegister,String> {

}
