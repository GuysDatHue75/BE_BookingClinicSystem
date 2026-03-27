package com.example.bookingclinic.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.entity.Patient;
import com.example.bookingclinic.user.service.UPatientService;

@RestController
@RequestMapping("api/v1")
public class UPatientController {
    private UPatientService patientService;

    public UPatientController(UPatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping("/patient/{id}") // xem thôn tin cá nhân của bệnh nhân
    public Patient patientDrtail(@PathVariable String id){
        return patientService.getPatientById(id);
    }

    @PutMapping("/patient/{id}") // chỉnh sửa thông tin cá nhân của bệnh nhân
    public Patient editPatient(@PathVariable String id, @RequestBody Patient newPatient){
        return patientService.editPatient(newPatient, id);
    }
}
