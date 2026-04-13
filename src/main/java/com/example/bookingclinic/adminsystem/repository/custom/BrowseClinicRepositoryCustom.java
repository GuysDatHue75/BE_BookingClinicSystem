package com.example.bookingclinic.adminsystem.repository.custom;

import java.util.List;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.entity.BrowseClinicEntity;
import com.example.bookingclinic.adminsystem.entity.ClinicEntity;

public interface BrowseClinicRepositoryCustom {
    List<ClinicEntity> search(BrowseClinicSearchRequest request);

    List<BrowseClinicEntity> searchBrowseClinic(BrowseClinicSearchRequest request);
}
