package com.example.bookingclinic.doctor.repository.PrescriptionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.doctor.entity.Prescription.MedicalRecords;

@Repository
public interface MedicalRecordsRepository extends JpaRepository<MedicalRecords, String> {
    @Query("SELECT MAX(CAST(SUBSTRING(m.maHoSo, 3) AS integer)) FROM MedicalRecords m")
    Integer getMaxMaHoSo();
}
