package com.example.bookingclinic.user.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.entity.Doctor;
import com.example.bookingclinic.user.entity.UClinic;
import com.example.bookingclinic.user.repository.UClinicRepository;
import com.example.bookingclinic.user.repository.UDoctorReponsitory;
import com.example.bookingclinic.user.service.UClinicService;
import com.example.bookingclinic.user.service.UDoctorService;

@RestController
@RequestMapping("/api/v1")
public class ServiceController {
    private UDoctorReponsitory doctorReponsitory;
    private UClinicRepository clinicRepository;
    private UDoctorService doctorService;
    private UClinicService clinicService;
    public ServiceController(UDoctorReponsitory doctorReponsitory, UClinicRepository clinicRepository, UDoctorService doctorService,UClinicService clinicService){

        this.doctorReponsitory = doctorReponsitory;
        this.clinicRepository = clinicRepository;
        this.doctorService = doctorService;
        this.clinicService = clinicService;
    }

    @GetMapping("/doctors") // Tìm kiếm bác sĩ theo tên & thành phố
    public List<Doctor> searchDoctors(@RequestParam String name, @RequestParam String tp) {
        if (name == null || name.isEmpty()) {
            return doctorReponsitory.findAll();
        }
        return doctorReponsitory
                .findByTaiKhoan_HoVaTenContainingIgnoreCaseAndTaiKhoan_VaiTroAndPhongKham_TinhThanhPhoContainingIgnoreCase(
                        name, "BacSi", tp);
    }

    @GetMapping("/doctors/{id}") // xem chi tiết 1 bác sĩ
    public Doctor doctorDetail(@PathVariable String id) {
        return doctorService.getDoctorDetail(id);
    }

    @GetMapping("/doctors/city/{tp}") // Xem tất cả bác sĩ của 1 tỉnh/ tp
    public Page<Doctor> getAllDoctorInCity(@PathVariable String tp, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Doctor> result = doctorService.getAllDoctorInCity(tp, pageable);
        return result;
    }

    @RequestMapping("clinic/doctors") // xem tất cả các bác sĩ của 1 phòng khám
    public List<Doctor> ListDoctorsInClinic(@RequestParam String id) {
        return doctorReponsitory.findByPhongKham_MaPhongKham(id);
    }

    @GetMapping("/clinics") // Tìm kiếm phòng khám theo tên & thành phố
    public List<UClinic> searchClinics(@RequestParam String name, @RequestParam String tp){
        if(name == null || name.isEmpty()){
            return clinicRepository.findAll();
        }
        return clinicRepository.findByTenPhongKhamContainingIgnoreCaseAndTinhThanhPhoContainingIgnoreCase(name, tp);
    }

    @GetMapping("/clinics/{id}") // xem thông tin chi tiết của 1 phòng khám
    public UClinic clinicDetail(@PathVariable String id){

        return clinicService.getClinicDtail(id);
    }

    @GetMapping("/clinics/city/{tp}") // Xem tất cả phòng khám của 1 tỉnh/ tp
    public Page<UClinic> getAllClinicsInCity(@PathVariable String tp, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "0") int size ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UClinic> result = clinicService.getAllClinicInCity(tp,pageable);
        return result;
    }

}
