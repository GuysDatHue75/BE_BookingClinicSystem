package com.example.bookingclinic.user.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.bookingclinic.user.service.UPrescriptionService;


@RestController
@RequestMapping("/api/v1")
public class UPrescriptionController {
    private UPrescriptionService prescriptionService;
    public UPrescriptionController(UPrescriptionService prescriptionService){

        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/history")
    public ResponseEntity<?> getMedicalHistory(
            @RequestParam String id) {

        return ResponseEntity.ok(
                prescriptionService.getMedicalHistory(id));
    }
}
