package com.example.bookingclinic.adminclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookingclinic.adminclinic.entity.SpecialtyClinicEntity;
import com.example.bookingclinic.adminclinic.entity.SpecialtyClinicId;

public interface SpecialtyClinicRepository extends JpaRepository<SpecialtyClinicEntity, SpecialtyClinicId>{
    
}
