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
            .orElseThrow(() -> new RuntimeException("không tìm thấy phòng khám"));
        
        entity.setTenPhongKham(request.getTenPhongKham());
        entity.setNgayThanhLap(request.getNgayThanhLap());
        // entity.setNgayDangKy(request.getNgayDangKy());
        // entity.setSoLuongBacSi(request.getSoLuongBacSi());
        entity.setLoaiHinhPhongKham(request.getLoaiHinhPhongKham());
        entity.setDiaChi(request.getDiaChi());
        entity.setTinhThanhPho(request.getTinhThanhPho());
        entity.setSoDienThoai(request.getSoDienThoai());
        entity.setEmail(request.getEmail());
        entity.setGioBatDauLamViec(request.getGioBatDauLamViec());
        entity.setGioKetThucLamViec(request.getGioKetThucLamViec());
        // entity.setTrangThai(request.getTrangThai());
        entity.setMoTa(request.getMoTa());
        // entity.setGiayPhep(request.getGiayPhep());
        entity.setNgayCap(request.getNgayCap());
        entity.setNoiCap(request.getNoiCap());
        entity.setNguoiDaiDien(request.getNguoiDaiDien());
        entity.setSoDienThoaiNguoiDaiDien(request.getSoDienThoaiNguoiDaiDien());

        if (anhPhongKham != null && !anhPhongKham.isEmpty()) {
            String anhUrl = fileUploadService.uploadFile(anhPhongKham, "anhPhongKham");
            entity.setAnhPhongKham(anhUrl); // Lưu đường dẫn "/uploads/anhPhongKham/xyz.jpg" vào DB
        }

        if (giayPhep != null && !giayPhep.isEmpty()) {
            String giayPhepUrl = fileUploadService.uploadFile(giayPhep, "giayPhep");
            entity.setGiayPhep(giayPhepUrl); // Lưu đường dẫn "/uploads/giayPhep/abc.pdf" vào DB
        }

        clinicRepository.save(entity);
    }

}
