package com.example.bookingclinic.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.user.entity.UAccount;
import com.example.bookingclinic.user.entity.UPatient;

@Repository
public interface UPatientRepository extends JpaRepository<UPatient, String> {
    UPatient findByEmail(String email);
    UPatient findByTaiKhoan_MaTaiKhoan(String id);
    UPatient findByTaiKhoan(UAccount account);
    UPatient findByMaBenhNhan(String id);
}
