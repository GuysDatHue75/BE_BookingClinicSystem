package com.example.bookingclinic.adminsystem.repository.custom;

import java.util.List;

import com.example.bookingclinic.adminsystem.dto.request.BrowseDoctorSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.BrowseDoctorDetailResponse;
import com.example.bookingclinic.adminsystem.repository.projection.BrowseDoctorProjection;

public interface BrowseDoctorRepositoryCustom {

    List<BrowseDoctorProjection> search(BrowseDoctorSearchRequest request);

    BrowseDoctorDetailResponse getDetail(String maBacSi);

}
