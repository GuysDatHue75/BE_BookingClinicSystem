package com.example.bookingclinic.doctor.repository.ScheduleRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.doctor.entity.Schedule.TimeSlot;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, String> {

}
