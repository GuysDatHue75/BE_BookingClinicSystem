package com.example.bookingclinic.adminclinic.repository.custom;

import com.example.bookingclinic.adminclinic.dto.request.SpecialtySearchRequest;
import com.example.bookingclinic.adminclinic.repository.projection.SpecialtyProjection;

import java.util.List;
import java.util.Optional;

public interface SpecialtyRepositoryCustom {
    Optional<SpecialtyProjection> findDetailByIdAndClinicId(String maPhongKham, String maChuyenKhoa);
    List<SpecialtyProjection> search(SpecialtySearchRequest request, String maPhongKham);
    List<SpecialtyProjection> findAllSpecialtyByClinicId(String maPhongKham);
}
