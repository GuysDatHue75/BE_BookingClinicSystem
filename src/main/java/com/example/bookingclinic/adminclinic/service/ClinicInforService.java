package com.example.bookingclinic.adminclinic.service;

import com.example.bookingclinic.adminclinic.dto.request.ClinicRequest;
import com.example.bookingclinic.adminclinic.dto.response.ClinicResponse;

public interface ClinicInforService {

    ClinicResponse getDetail(String maPhongKham);

    void updateClinic(String maPhongKham, ClinicRequest request);

}
