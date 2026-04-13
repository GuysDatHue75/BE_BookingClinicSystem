package com.example.bookingclinic.adminsystem.service;

import java.util.List;

import com.example.bookingclinic.adminsystem.dto.request.BrowseDoctorActionRequest;
import com.example.bookingclinic.adminsystem.dto.request.BrowseDoctorSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.BrowseDoctorDetailResponse;
import com.example.bookingclinic.adminsystem.repository.projection.BrowseDoctorProjection;

public interface BrowseDoctorService {

    List<BrowseDoctorProjection> search(BrowseDoctorSearchRequest request);

    void handleBrowseDoctor(BrowseDoctorActionRequest request);

    BrowseDoctorDetailResponse getDetail(String maBacSi);
}
