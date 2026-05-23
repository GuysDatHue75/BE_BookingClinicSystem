package com.example.bookingclinic.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.user.entity.UPrescription;

@Repository
public interface UPrescriptionRepository extends JpaRepository<UPrescription,String>{
    List<UPrescription> findByPatient_MaBenhNhan(String id);
    Optional<UPrescription> findByMedicalFile_MaHoSo(String maHoSo);
}
