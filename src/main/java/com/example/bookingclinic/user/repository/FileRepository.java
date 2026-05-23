package com.example.bookingclinic.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.user.entity.MedicalFile;

@Repository
public interface FileRepository extends JpaRepository<MedicalFile, String>{
    Optional<MedicalFile> findByCalendar_MaLichKham(String maLichKham);
}
