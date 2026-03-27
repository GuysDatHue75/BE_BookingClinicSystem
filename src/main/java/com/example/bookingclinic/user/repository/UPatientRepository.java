package com.example.bookingclinic.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.user.entity.Patient;

@Repository
public interface UPatientRepository extends JpaRepository<Patient, String> {
    Patient findByEmail(String email);
    Patient findByTaiKhoan_MaTaiKhoan(String id);
}
