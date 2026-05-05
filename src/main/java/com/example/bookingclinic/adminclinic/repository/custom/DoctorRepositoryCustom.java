package com.example.bookingclinic.adminclinic.repository.custom;

import java.util.List;

import com.example.bookingclinic.adminclinic.dto.request.DoctorSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.DoctorResponse;
import com.example.bookingclinic.adminclinic.repository.projection.DoctorProjection;


public interface DoctorRepositoryCustom {

    List<DoctorResponse> search(DoctorSearchRequest request, String maPhongKham);

    DoctorProjection getDetail(String maBacSi);

    List<DoctorResponse> findAllDoctors(String maPhongKham);

}
