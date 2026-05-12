package com.example.bookingclinic.doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.doctor.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {

}
