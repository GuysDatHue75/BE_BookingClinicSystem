package com.example.bookingclinic.adminsystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicDetailResponse;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicResponse;
import com.example.bookingclinic.adminsystem.entity.ClinicEntity;
import com.example.bookingclinic.adminsystem.entity.SubscriptionPackageEntity;
import com.example.bookingclinic.adminsystem.mapper.BrowseClinicMapper;
import com.example.bookingclinic.adminsystem.repository.ClinicRepository;
import com.example.bookingclinic.adminsystem.repository.SubscriptionPackageRepository;
import com.example.bookingclinic.adminsystem.service.ClinicService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRepository clinicRepository;
    private final BrowseClinicMapper browseClinicMapper;
    private final SubscriptionPackageRepository subscriptionPackageRepository;

    @Override
    public List<BrowseClinicResponse> getAll() {
        return browseClinicMapper.toResponseListFromClinic(clinicRepository.findByIsDeletedFalse());
    }

    @Override
    public List<BrowseClinicResponse> search(BrowseClinicSearchRequest request) {
        List<ClinicEntity> result = clinicRepository.searchClinic(request);
        return browseClinicMapper.toResponseListFromClinic(result);
    }

    @Override
    public List<BrowseClinicResponse> filter(BrowseClinicSearchRequest request) {
        List<ClinicEntity> result = clinicRepository.findAll();

        if(request.getTrangThai() != null && !request.getTrangThai().isEmpty()) {
             result = result.stream()
                .filter(c -> request.getTrangThai().equalsIgnoreCase(c.getTrangThai()))
                .toList();
        }

        if(request.getLoaiHinhPhongKham() != null && !request.getLoaiHinhPhongKham().isEmpty()) {
            result = result.stream()
                .filter(c -> request.getLoaiHinhPhongKham().equalsIgnoreCase(c.getLoaiHinhPhongKham()))
                .toList();
        }

        if(request.getMaGoi() != null && !request.getMaGoi().isEmpty()) {
            result = result.stream()
                .filter(c -> request.getMaGoi().equalsIgnoreCase(c.getMaGoi()))
                .toList();
        }

        if(request.getTinhThanhPho() != null && !request.getTinhThanhPho().isEmpty()) {
            result = result.stream()
                .filter(c -> request.getTinhThanhPho().equalsIgnoreCase(c.getTinhThanhPho()))
                .toList();
        }
        return browseClinicMapper.toResponseListFromClinic(result);
    }

    @Override
    public BrowseClinicDetailResponse getDetail(String maPhongKham) {
        ClinicEntity entity = clinicRepository.findById(maPhongKham)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng khám với mã: " + maPhongKham));

        return browseClinicMapper.toDetailResponseFromClinic(entity);
    }

    @Override
    @Transactional
    public void deleteClinic(String maPhongKham) {
        ClinicEntity entity = clinicRepository.findById(maPhongKham)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy"));

        entity.setTrangThai("Bị khóa");
        entity.setIsDeleted(true);
        clinicRepository.save(entity);
    }

    @Scheduled(cron = "0 0 0 * * ?") // mỗi ngày
    @Transactional
    public void autoExpire() {
        LocalDateTime now = LocalDateTime.now();

        List<ClinicEntity> expireClinic = clinicRepository.findByNgayHetHanBeforeAndIsDeletedFalse(now);

        for (ClinicEntity c : expireClinic) {
            c.setTrangThai("Ngừng hoạt động");
        }

        clinicRepository.saveAll(expireClinic);
    }

    @Override
    @Transactional
    public void renewClinic(String maPhongKham) {
        ClinicEntity entity = clinicRepository.findById(maPhongKham)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng khám với mã: " + maPhongKham));

        if(Boolean.TRUE.equals(entity.getIsDeleted())) {
            throw new RuntimeException("Phòng khám đã bị xóa (khóa)");
        }
        SubscriptionPackageEntity goi = subscriptionPackageRepository.findById(entity.getMaGoi())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy gói với mã: " + entity.getMaGoi()));
        int thoiHanNgay = goi.getThoiHanNgay();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime currentExpire = entity.getNgayHetHan();
        
        if(currentExpire != null && currentExpire.isAfter(now)){
            entity.setNgayHetHan(currentExpire.plusDays(thoiHanNgay));
        } else {
            entity.setNgayHetHan(now.plusDays(thoiHanNgay));
        }

        entity.setTrangThai("Hoạt động");
        clinicRepository.save(entity);
    }

}
