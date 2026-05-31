package com.example.bookingclinic.adminclinic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookingclinic.adminclinic.dto.request.DoctorRequest;
import com.example.bookingclinic.adminclinic.dto.request.DoctorSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.DoctorResponse;
import com.example.bookingclinic.adminclinic.dto.response.DoctorSimpleResponse;
import com.example.bookingclinic.adminclinic.repository.projection.DoctorProjection;
import com.example.bookingclinic.adminclinic.service.ClinicDoctorService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;





@RestController
@RequestMapping("/api/v1/adminclinic/doctor")
@RequiredArgsConstructor
public class ClinicDoctorController {
    
    private final ClinicDoctorService doctorService;

    @PostMapping(value = "/create/{maPhongKham}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createDoctor (@PathVariable String maPhongKham, @ModelAttribute DoctorRequest request,
            @RequestParam(value = "avt", required = false) MultipartFile avt,
            @RequestParam(value = "tepDinhKem", required = false) MultipartFile tepDinhKem
    ) {
        String response = doctorService.createDoctor(maPhongKham, request, avt, tepDinhKem);
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
    
    @GetMapping("/active-basic/{maPhongKham}")
    public ResponseEntity<List<DoctorSimpleResponse>> getActiveBasicDoctors(@PathVariable String maPhongKham) {
        List<DoctorSimpleResponse> doctors = doctorService.getActiveDoctors(maPhongKham);
        return ResponseEntity.ok(doctors);
    }
    
}
