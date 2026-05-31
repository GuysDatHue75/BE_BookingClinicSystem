package com.example.bookingclinic.adminclinic.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.bookingclinic.adminclinic.dto.request.DoctorRequest;
import com.example.bookingclinic.adminclinic.dto.request.DoctorSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.DoctorResponse;
import com.example.bookingclinic.adminclinic.dto.response.DoctorSimpleResponse;
import com.example.bookingclinic.adminclinic.repository.projection.DoctorProjection;

public interface ClinicDoctorService {
    String createDoctor(String maPhongKham, DoctorRequest request, MultipartFile avt, MultipartFile tepDinhKem);
    void deleteDoctor(String maBacSi);
    List<DoctorResponse> searchDoctor(DoctorSearchRequest request, String maPhongKham);
    DoctorProjection getDetailDoctor(String maBacSi);
    List<DoctorResponse> getAllDoctors(String maPhongKham);
    List<DoctorSimpleResponse> getActiveDoctors(String maPhongKham);
}
