package com.example.bookingclinic.adminclinic.service;

import java.util.List;

import com.example.bookingclinic.adminclinic.dto.request.SpecialtyRequest;
import com.example.bookingclinic.adminclinic.dto.request.SpecialtySearchRequest;
import com.example.bookingclinic.adminclinic.repository.projection.SpecialtyProjection;

public interface ClinicSpecialtyService {
    
    List<SpecialtyProjection> getAllSpecialty(String maPhongKham);

    SpecialtyProjection createSpecialty(SpecialtyRequest request, String maPhongKham);

    SpecialtyProjection updateSpecialty(String maChuyenKhoa, SpecialtyRequest request, String maPhongKham);

    void deleteSpecialty(String maChuyenKhoa, String maPhongKham);

    SpecialtyProjection getSpecialtyDetail(String maPhongKham, String maChuyenKhoa);

    List<SpecialtyProjection> searchSpecialty(SpecialtySearchRequest request, String maPhongKham);

}
