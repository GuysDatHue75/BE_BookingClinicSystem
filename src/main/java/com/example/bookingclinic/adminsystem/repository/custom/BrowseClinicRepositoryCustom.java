package com.example.bookingclinic.adminsystem.repository.custom;

import java.util.List;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.entity.BrowseClinicEntity;

public interface BrowseClinicRepositoryCustom {

    List<BrowseClinicEntity> searchBrowseClinic(BrowseClinicSearchRequest request);
}
