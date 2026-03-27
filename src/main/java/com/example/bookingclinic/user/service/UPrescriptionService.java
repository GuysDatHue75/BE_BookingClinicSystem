package com.example.bookingclinic.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.entity.Prescription;
import com.example.bookingclinic.user.repository.UPrescriptionRepository;

@Service
public class UPrescriptionService {
    private UPrescriptionRepository prescriptionRepository;

    public UPrescriptionService(UPrescriptionRepository prescriptionRepository){
        this.prescriptionRepository = prescriptionRepository;
    }

    public List<Prescription> getAllPrescriptioinsByID(String id){
        return prescriptionRepository.findByPatient_MaBenhNhan(id);
    }
}
