package com.example.bookingclinic.user.repository;

import com.example.bookingclinic.user.entity.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar,String> {
    List<Calendar> findByPatient_MaBenhNhanAndTrangThai(String id, String status);
}
