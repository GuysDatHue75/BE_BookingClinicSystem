package com.example.bookingclinic.adminclinic.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookingclinic.adminclinic.dto.TimeSlot;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, String>{

}
