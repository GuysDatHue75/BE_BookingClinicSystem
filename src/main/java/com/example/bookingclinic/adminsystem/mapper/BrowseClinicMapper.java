package com.example.bookingclinic.adminsystem.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicDetailResponse;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicResponse;
import com.example.bookingclinic.adminsystem.dto.response.ClinicDetailResponse;
import com.example.bookingclinic.adminsystem.entity.BrowseClinicEntity;
import com.example.bookingclinic.adminclinic.entity.ClinicEntity;

@Mapper(componentModel = "spring")
public interface BrowseClinicMapper {
    BrowseClinicResponse toResponseFromBrowseClinic(BrowseClinicEntity entity);

    List<BrowseClinicResponse> toResponseListBrowseClinic(List<BrowseClinicEntity> entities);

    BrowseClinicDetailResponse toDetailResponseFromBrowseClinic(BrowseClinicEntity entity);

    BrowseClinicResponse toResponseFromClinic(ClinicEntity entity);

    List<BrowseClinicResponse> toResponseListFromClinic(List<ClinicEntity> entities);

    ClinicDetailResponse toDetailResponseFromClinic(ClinicEntity entity);
    
}
