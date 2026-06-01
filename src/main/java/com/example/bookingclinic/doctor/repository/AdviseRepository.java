package com.example.bookingclinic.doctor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookingclinic.user.entity.Advisory;

public interface AdviseRepository extends JpaRepository<Advisory, String> {
    List<Advisory> findByTrangThaiTraLoiFalseOrderByThoiGianHoiAsc();

}
