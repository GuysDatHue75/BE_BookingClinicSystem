package com.example.bookingclinic.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.user.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, String>{
    Optional<File> findByCalendar_MaLichKham(String maLichKham);
}
