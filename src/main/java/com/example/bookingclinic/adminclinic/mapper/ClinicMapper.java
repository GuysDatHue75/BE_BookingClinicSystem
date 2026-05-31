package com.example.bookingclinic.adminclinic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.bookingclinic.adminclinic.dto.response.ClinicResponse;
import com.example.bookingclinic.adminclinic.entity.ClinicEntity;

@Mapper(componentModel = "spring")
public interface ClinicMapper {
    @Mapping(source = "subpackage.maGoi", target = "maGoi")
    @Mapping(source = "subpackage.tenGoi", target = "tenGoi")
    @Mapping(source = "account.maTaiKhoan", target = "maTaiKhoan")
    @Mapping(source = "account.soDt", target = "soDt")
    // @Mapping(source = "account.matKhau", target = "matKhau")
    ClinicResponse toDetailResponseFromClinic(ClinicEntity entity);
}
