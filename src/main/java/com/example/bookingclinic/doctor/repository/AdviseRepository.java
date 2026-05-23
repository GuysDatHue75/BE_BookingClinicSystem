package com.example.bookingclinic.doctor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookingclinic.doctor.entity.Advise;

public interface AdviseRepository extends JpaRepository<Advise, String> {
    List<Advise> findByTrangThaiFalseOrderByThoiGianHoiAsc();

}
