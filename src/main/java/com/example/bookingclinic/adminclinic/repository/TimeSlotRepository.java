package com.example.bookingclinic.adminclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookingclinic.adminclinic.entity.TimeSlotsEntity;

public interface TimeSlotRepository extends JpaRepository<TimeSlotsEntity, String>{
    List<TimeSlotsEntity> findByShift_MaCaLamViec(String maCaLamViec);
}
