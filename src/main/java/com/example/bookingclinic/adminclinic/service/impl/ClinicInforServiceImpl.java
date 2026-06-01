package com.example.bookingclinic.adminclinic.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookingclinic.adminclinic.dto.request.ClinicRequest;
import com.example.bookingclinic.adminclinic.dto.response.ClinicResponse;
import com.example.bookingclinic.adminclinic.entity.ClinicEntity;
import com.example.bookingclinic.adminclinic.mapper.ClinicMapper;
import com.example.bookingclinic.adminclinic.repository.ClinicRepository;
import com.example.bookingclinic.adminclinic.service.ClinicInforService;
import com.example.bookingclinic.adminclinic.service.FileUploadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClinicInforServiceImpl implements ClinicInforService{
    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;
    private final FileUploadService fileUploadService;

    @Override
    public ClinicResponse getDetail(String maPhongKham){
        ClinicEntity entity = clinicRepository.findById(maPhongKham)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng khám với mã: " + maPhongKham));
        
        return clinicMapper.toDetailResponseFromClinic(entity);
    }

    @Override
@Transactional
public void updateClinic(String maPhongKham, ClinicRequest request, MultipartFile anhPhongKham, MultipartFile giayPhep){
    ClinicEntity entity = clinicRepository.findById(maPhongKham)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng khám"));
    
    // Áp dụng Partial Update: Chỉ cập nhật khi có dữ liệu thật
    if (request.getTenPhongKham() != null && !request.getTenPhongKham().trim().isEmpty()) {
        entity.setTenPhongKham(request.getTenPhongKham());
    }
    if (request.getNgayThanhLap() != null) {
        entity.setNgayThanhLap(request.getNgayThanhLap());
    }
    if (request.getLoaiHinhPhongKham() != null && !request.getLoaiHinhPhongKham().trim().isEmpty()) {
        entity.setLoaiHinhPhongKham(request.getLoaiHinhPhongKham());
    }
    // Ngăn chặn triệt để lỗi gán NULL cho địa chỉ
    if (request.getDiaChi() != null && !request.getDiaChi().trim().isEmpty()) {
        entity.setDiaChi(request.getDiaChi());
    }
    if (request.getTinhThanhPho() != null && !request.getTinhThanhPho().trim().isEmpty()) {
        entity.setTinhThanhPho(request.getTinhThanhPho());
    }
    if (request.getSoDienThoai() != null && !request.getSoDienThoai().trim().isEmpty()) {
        entity.setSoDienThoai(request.getSoDienThoai());
    }
    if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
        entity.setEmail(request.getEmail());
    }
    if (request.getGioBatDauLamViec() != null) {
        entity.setGioBatDauLamViec(request.getGioBatDauLamViec());
    }
    if (request.getGioKetThucLamViec() != null) {
        entity.setGioKetThucLamViec(request.getGioKetThucLamViec());
    }
    if (request.getMoTa() != null) {
        entity.setMoTa(request.getMoTa());
    }
    if (request.getNgayCap() != null) {
        entity.setNgayCap(request.getNgayCap());
    }
    if (request.getNoiCap() != null) {
        entity.setNoiCap(request.getNoiCap());
    }
    if (request.getNguoiDaiDien() != null && !request.getNguoiDaiDien().trim().isEmpty()) {
        entity.setNguoiDaiDien(request.getNguoiDaiDien());
    }
    if (request.getSoDienThoaiNguoiDaiDien() != null) {
        entity.setSoDienThoaiNguoiDaiDien(request.getSoDienThoaiNguoiDaiDien());
    }

    if (anhPhongKham != null && !anhPhongKham.isEmpty()) {
        String anhUrl = fileUploadService.uploadFile(anhPhongKham, "anhPhongKham");
        entity.setAnhPhongKham(anhUrl);
    }

    if (giayPhep != null && !giayPhep.isEmpty()) {
        String giayPhepUrl = fileUploadService.uploadFile(giayPhep, "giayPhep");
        entity.setGiayPhep(giayPhepUrl);
    }

    clinicRepository.save(entity);
    }

}
