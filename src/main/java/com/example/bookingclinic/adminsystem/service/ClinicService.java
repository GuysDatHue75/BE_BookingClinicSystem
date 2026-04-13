package com.example.bookingclinic.adminsystem.service;

import java.util.List;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicDetailResponse;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicResponse;

public interface ClinicService {

    List<BrowseClinicResponse> getAll();

    List<BrowseClinicResponse> search(BrowseClinicSearchRequest request);

    List<BrowseClinicResponse> filter(BrowseClinicSearchRequest request);

    BrowseClinicDetailResponse getDetail(String maPhongKham);

    void deleteClinic(String maPhongKham);

    void renewClinic(String maPhongKham);

}
