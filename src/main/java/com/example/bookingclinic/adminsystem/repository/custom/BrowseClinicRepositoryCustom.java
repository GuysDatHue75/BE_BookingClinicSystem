package com.example.bookingclinic.adminsystem.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.entity.BrowseClinicEntity;

public interface BrowseClinicRepositoryCustom {

    Page<BrowseClinicEntity> searchBrowseClinic(BrowseClinicSearchRequest request, Pageable pageable);
}
