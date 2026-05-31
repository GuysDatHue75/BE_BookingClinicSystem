package com.example.bookingclinic.adminclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookingclinic.doctor.entity.Schedule.WorkShift;

public interface ClinicWorkShiftRepository extends JpaRepository<WorkShift, String>{
    
}
