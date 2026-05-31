package com.example.bookingclinic.doctor.repository.PrescriptionRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookingclinic.doctor.entity.Prescription.FilePrescription;

public interface FilePrescriptionRepository extends JpaRepository<FilePrescription, Integer> {
    List<FilePrescription> findByMaHoSo(String maHoSo);
}
