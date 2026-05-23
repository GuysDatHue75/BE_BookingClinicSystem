package com.example.bookingclinic.adminsystem.service;

import org.springframework.data.domain.Page;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicResponse;
import com.example.bookingclinic.adminsystem.dto.response.ClinicDetailResponse;

public interface SystemClinicService {

    Page<BrowseClinicResponse> getAll(BrowseClinicSearchRequest request);

    Page<BrowseClinicResponse> search(BrowseClinicSearchRequest request);

    ClinicDetailResponse getDetail(String maPhongKham);

    void deleteClinic(String maPhongKham);

    void renewClinic(String maPhongKham);

}
