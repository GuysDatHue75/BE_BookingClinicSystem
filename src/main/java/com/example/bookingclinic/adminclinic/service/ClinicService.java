package com.example.bookingclinic.adminclinic.service;

import com.example.bookingclinic.adminclinic.dto.request.ClinicRequest;
import com.example.bookingclinic.adminclinic.dto.response.ClinicResponse;

public interface ClinicService {

    ClinicResponse getDetail(String maPhongKham);

    void updateClinic(ClinicRequest request);
    
}
