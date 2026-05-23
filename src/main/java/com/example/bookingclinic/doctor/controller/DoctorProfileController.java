package com.example.bookingclinic.doctor.controller;

import com.example.bookingclinic.doctor.dto.DoctorProfileDTO;
import com.example.bookingclinic.doctor.entity.Doctor;
import com.example.bookingclinic.doctor.service.DoctorProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctor/profile")
@RequiredArgsConstructor
public class DoctorProfileController {

    private final DoctorProfileService doctorProfileService;

    // Lấy thông tin Profile
    @GetMapping("/{maBacSi}")
    public ResponseEntity<Doctor> getProfile(@PathVariable String maBacSi) {
        Doctor profile = doctorProfileService.getDoctorProfile(maBacSi);
        return ResponseEntity.ok(profile);
    }

    // Cập nhật thông tin Profile
    @PutMapping("/{maBacSi}")
    public ResponseEntity<DoctorProfileDTO> updateProfile(
            @PathVariable String maBacSi,
            @RequestBody DoctorProfileDTO updateDTO) {

        DoctorProfileDTO updatedProfile = doctorProfileService.updateDoctorProfile(maBacSi, updateDTO);
        return ResponseEntity.ok(updatedProfile);
    }
}