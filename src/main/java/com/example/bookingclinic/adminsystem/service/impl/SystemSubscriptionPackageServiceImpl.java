package com.example.bookingclinic.adminsystem.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.adminsystem.dto.request.SubscriptionPackageRequest;
import com.example.bookingclinic.adminsystem.dto.request.SubscriptionPackageSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.SubscriptionPackageResponse;
import com.example.bookingclinic.adminsystem.entity.FeaturesEntity;
import com.example.bookingclinic.adminsystem.entity.SubscriptionPackageEntity;
import com.example.bookingclinic.adminsystem.entity.SubscriptionPackageFeaturesEntity;
import com.example.bookingclinic.adminsystem.entity.SubscriptionPackageFeaturesId;
import com.example.bookingclinic.adminsystem.repository.FeaturesRepository;
import com.example.bookingclinic.adminsystem.repository.SubscriptionPackageFeaturesRepository;
import com.example.bookingclinic.adminsystem.repository.SubscriptionPackageRepository;
import com.example.bookingclinic.adminsystem.service.SystemSubscriptionPackageService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SystemSubscriptionPackageServiceImpl implements SystemSubscriptionPackageService{
    private final SubscriptionPackageRepository sPackageRepository;
    private final FeaturesRepository fRepository;
    private final SubscriptionPackageFeaturesRepository spFeaturesRepository;

    @Override
    public Page<SubscriptionPackageResponse> search(SubscriptionPackageSearchRequest request){
        return sPackageRepository.search(request);
    }

    private void saveFeatures(SubscriptionPackageEntity sPackageEntity, List<String> danhSachMaTinhNang){
        if(danhSachMaTinhNang == null || danhSachMaTinhNang.isEmpty()) return;

        for(String maTN : danhSachMaTinhNang) {
            FeaturesEntity feature = fRepository.findById(maTN)
                .orElseThrow(() -> new RuntimeException("Không có tính năng này"));
            SubscriptionPackageFeaturesEntity spFeaturesEntity = new SubscriptionPackageFeaturesEntity();
            spFeaturesEntity.setId(new SubscriptionPackageFeaturesId(sPackageEntity.getMaGoi(), maTN));
            spFeaturesEntity.setSubscriptionPackage(sPackageEntity);
            spFeaturesEntity.setFeatures(feature);
            
            spFeaturesRepository.save(spFeaturesEntity);
        }
    }

    private String generateMaGoi(){
        List<String> danhSachMaGoi = sPackageRepository.findAllMaGoi();
        int maxNumber = 0;
        for(String ma : danhSachMaGoi) {
            if(ma != null && ma.startsWith("G")) {
                try {
                    int currentNumber = Integer.parseInt(ma.substring(1));
                    if(currentNumber > maxNumber) {
                        maxNumber = currentNumber;
                    }
                } catch(NumberFormatException e) {
                    // Ignore non-numeric suffix
                }
            }
        }
        int nextNumber = maxNumber + 1;
        return String.format("G%02d", nextNumber);
    }

    @Override
    public void createPackage(SubscriptionPackageRequest request) {
        String maGoi = generateMaGoi();
        SubscriptionPackageEntity sPackageEntity = SubscriptionPackageEntity.builder()
            .maGoi(maGoi)
            .tenGoi(request.getTenGoi())
            .gia(request.getGia())
            .thoiHanNgay(request.getThoiHanNgay())
            .moTa(request.getMoTa())
            .trangThai("Hoạt động")
            .isDeleted(false)
            .build();
        sPackageRepository.save(sPackageEntity);

        saveFeatures(sPackageEntity, request.getDanhSachMaTinhNang());
    }

    @Override
    public void updatePackage(SubscriptionPackageRequest request){
        SubscriptionPackageEntity sPackageEntity = sPackageRepository.findById(request.getMaGoi())
            .orElseThrow(() -> new RuntimeException("Không tồn tại mã gói này."));
        
        if (Boolean.TRUE.equals(sPackageEntity.getIsDeleted())) {
            throw new RuntimeException("Gói đăng ký này đã bị xóa, không thể chỉnh sửa.");
        }
        sPackageEntity.setTenGoi(request.getTenGoi());
        sPackageEntity.setGia(request.getGia());
        sPackageEntity.setThoiHanNgay(request.getThoiHanNgay());
        sPackageEntity.setMoTa(request.getMoTa());
        sPackageEntity.setTrangThai(request.getTrangThai());

        sPackageRepository.save(sPackageEntity);

        spFeaturesRepository.deleteBySubscriptionPackageMaGoi(request.getMaGoi());

        saveFeatures(sPackageEntity, request.getDanhSachMaTinhNang());
    }

    @Override
    public void deletePackage(String maGoi){
        SubscriptionPackageEntity sPackageEntity = sPackageRepository.findById(maGoi)
            .orElseThrow(() -> new RuntimeException("Không tồn tại mã gói này"));
        
        if (Boolean.TRUE.equals(sPackageEntity.getIsDeleted())) {
            throw new RuntimeException("Gói đăng ký này đã bị xóa từ trước, không thể thực hiện lại thao tác này.");
        }
        sPackageEntity.setTrangThai("Đã xóa");
        sPackageEntity.setIsDeleted(true);
        sPackageRepository.save(sPackageEntity);
    }

}
