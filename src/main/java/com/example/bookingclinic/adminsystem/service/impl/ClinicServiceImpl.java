package com.example.bookingclinic.adminsystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicResponse;
import com.example.bookingclinic.adminsystem.dto.response.ClinicDetailResponse;
import com.example.bookingclinic.adminsystem.entity.AccountEntity;
import com.example.bookingclinic.adminsystem.entity.ClinicEntity;
import com.example.bookingclinic.adminsystem.entity.SubscriptionPackageEntity;
import com.example.bookingclinic.adminsystem.mapper.BrowseClinicMapper;
import com.example.bookingclinic.adminsystem.repository.AccountRepository;
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
    private final AccountRepository accountRepository;

    @Override
    public Page<BrowseClinicResponse> getAll(BrowseClinicSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(),request.getSize());
        Page<ClinicEntity> clinicPage = clinicRepository.findByIsDeletedFalse(pageable);
        return clinicPage.map(entity -> browseClinicMapper.toResponseFromClinic(entity));
    }

    @Override
    public Page<BrowseClinicResponse> search(BrowseClinicSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(),request.getSize());
        Page<ClinicEntity> result = clinicRepository.searchClinic(request, pageable);
        return result.map(entity -> browseClinicMapper.toResponseFromClinic(entity));
    }

    @Override
    public ClinicDetailResponse getDetail(String maPhongKham) {
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

        AccountEntity account = accountRepository.findById(entity.getAccount().getMaTaiKhoan())
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

        account.setIsDeleted(true);
        account.setTrangThai(false);
        accountRepository.save(account);
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
        SubscriptionPackageEntity goi = subscriptionPackageRepository.findById(entity.getSubpackage().getMaGoi())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy gói với mã: " + entity.getSubpackage().getMaGoi()));
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
