package com.example.bookingclinic.adminsystem.repository.custom;

import java.util.List;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.entity.ClinicEntity;

public interface ClinicRepositoryCustom {

    List<ClinicEntity> searchClinic(BrowseClinicSearchRequest request);

}
