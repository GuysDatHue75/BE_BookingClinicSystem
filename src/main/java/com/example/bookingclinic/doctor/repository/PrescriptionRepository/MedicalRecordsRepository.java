package com.example.bookingclinic.doctor.repository.PrescriptionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.doctor.entity.Prescription.MedicalRecords;

@Repository
public interface MedicalRecordsRepository extends JpaRepository<MedicalRecords, String> {

}
