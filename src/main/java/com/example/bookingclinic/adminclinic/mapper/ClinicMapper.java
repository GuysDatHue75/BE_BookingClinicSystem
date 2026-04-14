package com.example.bookingclinic.adminclinic.mapper;

import org.mapstruct.Mapper;

import com.example.bookingclinic.adminclinic.dto.response.ClinicResponse;
import com.example.bookingclinic.adminclinic.entity.ClinicEntity;

@Mapper(componentModel = "spring")
public interface ClinicMapper {
    ClinicResponse toDetailResponseFromClinic(ClinicEntity entity);
}
