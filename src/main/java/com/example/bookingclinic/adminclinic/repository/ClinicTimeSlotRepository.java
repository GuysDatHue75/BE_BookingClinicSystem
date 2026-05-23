package com.example.bookingclinic.adminclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookingclinic.doctor.entity.Schedule.TimeSlot;

public interface ClinicTimeSlotRepository extends JpaRepository<TimeSlot, String>{
    List<TimeSlot> findByCaLamViec_MaCaLamViec(String maCaLamViec);

}
