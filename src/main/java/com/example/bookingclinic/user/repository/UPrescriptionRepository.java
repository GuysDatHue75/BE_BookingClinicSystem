package com.example.bookingclinic.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.user.entity.Prescription;

@Repository
public interface UPrescriptionRepository extends JpaRepository<Prescription,String>{
    List<Prescription> findByPatient_MaBenhNhan(String id);
}
