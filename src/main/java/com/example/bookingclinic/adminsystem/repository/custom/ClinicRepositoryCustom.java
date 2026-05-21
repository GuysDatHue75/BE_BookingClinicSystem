package com.example.bookingclinic.adminsystem.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.entity.ClinicEntity;

public interface ClinicRepositoryCustom {

    Page<ClinicEntity> searchClinic(BrowseClinicSearchRequest request, Pageable pageable);

}
