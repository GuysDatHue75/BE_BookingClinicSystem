package com.example.bookingclinic.adminclinic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminclinic.dto.request.DoctorRequest;
import com.example.bookingclinic.adminclinic.dto.request.DoctorSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.DoctorResponse;
import com.example.bookingclinic.adminclinic.repository.projection.DoctorProjection;
import com.example.bookingclinic.adminclinic.service.ClinicDoctorService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("/api/v1/adminclinic/doctor")
@RequiredArgsConstructor
public class ClinicDoctorController {
    
    private final ClinicDoctorService doctorService;

    @PostMapping("/create/{maPhongKham}")
    public ResponseEntity<String> createDoctor(@RequestBody DoctorRequest request, @PathVariable String maPhongKham) {
        String response = doctorService.createDoctor(request, maPhongKham);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/delete/{maBacSi}")
    public ResponseEntity<String> deleteDoctor(@PathVariable String maBacSi) {
        doctorService.deleteDoctor(maBacSi);
        return ResponseEntity.ok("Xóa bác sĩ thành công");
    }

    @PostMapping("/search/{maPhongKham}")
    public ResponseEntity<List<DoctorResponse>> searchDoctors(@RequestBody DoctorSearchRequest request, @PathVariable String maPhongKham) {
        List<DoctorResponse> result = doctorService.searchDoctor(request, maPhongKham);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/detail/{maBacSi}")
    public ResponseEntity<DoctorProjection> getDoctorDetail(@PathVariable String maBacSi) {
        DoctorProjection detail = doctorService.getDetailDoctor(maBacSi);
        return ResponseEntity.ok(detail);
    }

    @GetMapping("all/{maPhongKham}")
    public ResponseEntity<List<DoctorResponse>> getAllDoctors(@PathVariable String maPhongKham) {
        List<DoctorResponse> doctors = doctorService.getAllDoctors(maPhongKham);
        return ResponseEntity.ok(doctors);
    }
    
    
    
    
}
