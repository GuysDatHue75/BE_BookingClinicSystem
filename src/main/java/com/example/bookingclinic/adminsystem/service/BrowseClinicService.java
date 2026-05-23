package com.example.bookingclinic.adminsystem.service;

import org.springframework.data.domain.Page;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicActionRequest;
import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicDetailResponse;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicResponse;

public interface BrowseClinicService {

    Page<BrowseClinicResponse> getAll(int page, int size);

    Page<BrowseClinicResponse> search(BrowseClinicSearchRequest request);

    Page<BrowseClinicResponse> getPending(int papge, int size);

    void handleBrowseClinic(BrowseClinicActionRequest request);

    BrowseClinicDetailResponse getDetail(String maPhongKham);
}