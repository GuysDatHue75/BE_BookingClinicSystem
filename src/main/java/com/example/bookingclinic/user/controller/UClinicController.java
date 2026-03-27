package com.example.bookingclinic.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.entity.UClinic;
import com.example.bookingclinic.user.service.UClinicService;

@RestController
@RequestMapping("/api/v1")
public class UClinicController {
    private UClinicService clinicService;

    public UClinicController(UClinicService clinicService){
        this.clinicService = clinicService;
    }

    @PostMapping("/clinic") // đăng ký 1 phòng khám mới
    public void registerClinic(@RequestBody UClinic clinic){
        clinicService.registerClinic(clinic);
    }
    @GetMapping("/specialty/clinics") // lọc các phòng khám theo chuyên khoa
    public List<UClinic> filterClinicByCityAndSpecialty(@RequestParam String tp, @RequestParam String id){
        return clinicService.findClinicsByCityAndSpecialty(tp, id);
    }
}
