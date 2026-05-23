package com.example.bookingclinic.user.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.entity.UClinic;
import com.example.bookingclinic.user.repository.UClinicRepository;
import com.example.bookingclinic.user.service.UClinicService;

@RestController
@RequestMapping("/api/v1")
public class ClinicController {
    private UClinicService clinicService;
    private UClinicRepository clinicRepository;

    public ClinicController(UClinicService clinicService, UClinicRepository clinicRepository){
        this.clinicService = clinicService;
        this.clinicRepository = clinicRepository;
    }

   
    @GetMapping("/specialty/clinics") // lọc các phòng khám theo chuyên khoa
    public Page<UClinic> filterClinicByCityAndSpecialty(@RequestParam String tp, @RequestParam String id, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "0") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<UClinic> result = clinicService.findClinicsByCityAndSpecialty(tp, id,pageable);
        return result;
    }

    @GetMapping("/clinics-top-8") // lấy ra 8 phòng khám có số sao lớn nhất
    public List<UClinic> getClinicsTop8(@RequestParam String city){
        return clinicRepository.getClinicsTop8(city);
    }
}
