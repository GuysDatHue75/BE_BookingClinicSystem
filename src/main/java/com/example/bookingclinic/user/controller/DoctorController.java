package com.example.bookingclinic.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.entity.Doctor;
import com.example.bookingclinic.user.service.UDoctorService;

@RestController
@RequestMapping("/api/v1")
public class DoctorController {
    private UDoctorService doctorService;

    public DoctorController(UDoctorService doctorService){
        this.doctorService = doctorService;
    }

    @GetMapping("/doctorAll/doctors") //filter bác sĩ theo học hàm và chuyên khoa
    public List<Doctor> filterDoctorsByHocHamAndChuyenKhoa(@RequestParam String hh,@RequestParam String ck,@RequestParam String tp){
        return doctorService.filterDoctorsByHocHamOrChuyenKhoa(hh, ck, tp);
    }
}
