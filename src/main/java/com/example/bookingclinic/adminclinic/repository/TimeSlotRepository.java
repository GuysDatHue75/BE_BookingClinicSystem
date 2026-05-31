package com.example.bookingclinic.adminclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookingclinic.doctor.entity.Schedule.TimeSlot;


public interface TimeSlotRepository extends JpaRepository<TimeSlot, String>{

}
