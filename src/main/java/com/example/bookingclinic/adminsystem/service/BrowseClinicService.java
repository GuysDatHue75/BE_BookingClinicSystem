package com.example.bookingclinic.adminsystem.service;

import java.util.List;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicActionRequest;
import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicDetailResponse;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicResponse;

public interface BrowseClinicService {

    List<BrowseClinicResponse> getAll();

    List<BrowseClinicResponse> search(BrowseClinicSearchRequest request);

    List<BrowseClinicResponse> getPending();

    void handleBrowseClinic(BrowseClinicActionRequest request);

    List<BrowseClinicResponse> filter(BrowseClinicSearchRequest request);

    BrowseClinicDetailResponse getDetail(String maPhongKham);
}