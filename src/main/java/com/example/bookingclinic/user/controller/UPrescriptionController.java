package com.example.bookingclinic.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.entity.Prescription;
import com.example.bookingclinic.user.service.UPrescriptionService;

@RestController
@RequestMapping("/api/v1")
public class UPrescriptionController {
    private UPrescriptionService prescriptionService;

    public UPrescriptionController(UPrescriptionService prescriptionService){
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/prescriptions")// Xem hóa đơn thuốc, lịch khám, lịch sử khám
    public List<Prescription> getAllPrescriptsById(@RequestParam String id){
        return prescriptionService.getAllPrescriptioinsByID(id);
    }
}
