package com.example.bookingclinic.adminsystem.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicDetailResponse;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicResponse;
import com.example.bookingclinic.adminsystem.entity.BrowseClinicEntity;
import com.example.bookingclinic.adminsystem.entity.ClinicEntity;

@Mapper(componentModel = "spring")
public interface BrowseClinicMapper {
    BrowseClinicResponse toResponseFromBrowseClinic(BrowseClinicEntity entity);

    List<BrowseClinicResponse> toResponseListBrowseClinic(List<BrowseClinicEntity> entities);

    BrowseClinicDetailResponse toDetailResponseFromBrowseClinic(BrowseClinicEntity entity);

    BrowseClinicResponse toResponseFromClinic(ClinicEntity entity);

    List<BrowseClinicResponse> toResponseListFromClinic(List<ClinicEntity> entities);

    BrowseClinicDetailResponse toDetailResponseFromClinic(ClinicEntity entity);
    
}
    // public BrowseClinicResponse toResponse(BrowseClinicEntity entity) {
    //     return BrowseClinicResponse.builder()
    //             .maPhongKham(entity.getMaPhongKham())
    //             .tenPhongKham(entity.getTenPhongKham())
    //             .tinhThanhPho(entity.getTinhThanhPho())
    //             .diaChi(entity.getDiaChi())
    //             .nguoiDaiDien(entity.getNguoiDaiDien())
    //             .soDienThoai(entity.getSoDienThoai())
    //             .email(entity.getEmail())
    //             .trangThai(entity.getTrangThai())
    //             .ngayDangKy(entity.getNgayDangKy())
    //             .build();
    // }
