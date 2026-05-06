package com.example.bookingclinic.adminsystem.service;

import java.util.List;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicResponse;
import com.example.bookingclinic.adminsystem.dto.response.ClinicDetailResponse;

public interface ClinicService {

    List<BrowseClinicResponse> getAll();

    List<BrowseClinicResponse> search(BrowseClinicSearchRequest request);

    List<BrowseClinicResponse> filter(BrowseClinicSearchRequest request);

    ClinicDetailResponse getDetail(String maPhongKham);

    void deleteClinic(String maPhongKham);

    void renewClinic(String maPhongKham);

}
