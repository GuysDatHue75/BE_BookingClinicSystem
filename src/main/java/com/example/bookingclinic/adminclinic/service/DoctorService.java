package com.example.bookingclinic.adminclinic.service;

import java.util.List;

import com.example.bookingclinic.adminclinic.dto.request.DoctorRequest;
import com.example.bookingclinic.adminclinic.dto.request.DoctorSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.DoctorResponse;
import com.example.bookingclinic.adminclinic.repository.projection.DoctorProjection;

public interface DoctorService {
    String createDoctor(DoctorRequest request, String maPhongKham);
    void deleteDoctor(String maBacSi);
    List<DoctorResponse> searchDoctor(DoctorSearchRequest request, String maPhongKham);
    DoctorProjection getDetailDoctor(String maBacSi);
    List<DoctorResponse> getAllDoctors(String maPhongKham);
}
