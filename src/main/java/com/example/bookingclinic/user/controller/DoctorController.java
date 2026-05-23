package com.example.bookingclinic.user.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.entity.UDoctor;
import com.example.bookingclinic.user.service.UDoctorService;

import com.example.bookingclinic.user.repository.UDoctorReponsitory;


@RestController
@RequestMapping("/api/v1")
public class DoctorController {
    private UDoctorService doctorService;
    private UDoctorReponsitory uDoctorReponsitory;
    public DoctorController(UDoctorService doctorService, UDoctorReponsitory uDoctorReponsitory){
        this.doctorService = doctorService;
        this.uDoctorReponsitory = uDoctorReponsitory;
    }

    @GetMapping("/doctorAll/doctors") //filter bác sĩ theo học hàm và chuyên khoa
    public Page<UDoctor> filterDoctorsByHocHamAndChuyenKhoa(@RequestParam String hh,@RequestParam String ck,@RequestParam String tp,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "0") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<UDoctor> result = doctorService.filterDoctorsByHocHamOrChuyenKhoa(hh, ck, tp, pageable);
        return result;
    }
    @GetMapping("/doctors-top-8") // lấy ra 8 bác sĩ có số sao lớn nhất
    public List<UDoctor> getDoctorTop8(@RequestParam String city){
        return uDoctorReponsitory.getDoctorsTop8(city);
    }
}
