package com.example.bookingclinic.adminsystem.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminsystem.dto.request.SubscriptionPackageSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.SubscriptionPackageResponse;
import com.example.bookingclinic.adminsystem.entity.QFeaturesEntity;
import com.example.bookingclinic.adminsystem.entity.QSubscriptionPackageEntity;
import com.example.bookingclinic.adminsystem.entity.QSubscriptionPackageFeaturesEntity;
import com.example.bookingclinic.adminsystem.repository.custom.SubscriptionPackageRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class SubscriptionPackageRepositoryImpl implements SubscriptionPackageRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<SubscriptionPackageResponse> search(SubscriptionPackageSearchRequest request) {
        QSubscriptionPackageEntity goi = QSubscriptionPackageEntity.subscriptionPackageEntity;
        QSubscriptionPackageFeaturesEntity spf = QSubscriptionPackageFeaturesEntity.subscriptionPackageFeaturesEntity;
        QFeaturesEntity tinhNang = QFeaturesEntity.featuresEntity;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(goi.isDeleted.eq(false));

        if(request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            builder.and(goi.tenGoi.containsIgnoreCase(request.getKeyword()));
        }

        if(request.getTrangThai() != null && !request.getTrangThai().isEmpty()) {
            builder.and(goi.trangThai.eq(request.getTrangThai()));
        }

        if(request.getThoiHanNgay() != null) {
            builder.and(goi.thoiHanNgay.eq(request.getThoiHanNgay()));
        }

        List<String> ids = queryFactory
                .select(goi.maGoi)
                .from(goi)
                .where(builder)
                .offset((long) request.getPage() * request.getSize())
                .limit(request.getSize())
                .fetch();
        
        if (ids.isEmpty()) {
            return new PageImpl<>(new ArrayList<>(), PageRequest.of(request.getPage(), request.getSize()), 0);
        }

        List<Tuple> rawResults = queryFactory
                .select(
                        goi.maGoi,
                        goi.tenGoi,
                        goi.gia,
                        goi.thoiHanNgay,
                        goi.moTa,
                        goi.trangThai,
                        tinhNang.tenTinhNang
                )
                .from(goi)
                .leftJoin(spf).on(spf.subscriptionPackage.eq(goi))
                .leftJoin(tinhNang).on(spf.features.eq(tinhNang))
                .where(goi.maGoi.in(ids))
                .fetch();
        
        Map<String, SubscriptionPackageResponse> map = new HashMap<>();

        for (Tuple row : rawResults) {
            String maGoi = row.get(goi.maGoi);
            
            SubscriptionPackageResponse response = map.computeIfAbsent(maGoi, key -> {
                SubscriptionPackageResponse newRes = new SubscriptionPackageResponse();
                newRes.setMaGoi(key);
                newRes.setTenGoi(row.get(goi.tenGoi));
                newRes.setGia(row.get(goi.gia));
                newRes.setThoiHanNgay(row.get(goi.thoiHanNgay));
                newRes.setMoTa(row.get(goi.moTa));
                newRes.setTrangThai(row.get(goi.trangThai));
                newRes.setDanhSachTenTinhNang(new ArrayList<>());
                return newRes;
            });
            String featureName = row.get(tinhNang.tenTinhNang);
            if (featureName != null) {
                response.getDanhSachTenTinhNang().add(featureName);
            }
        }
        
        List<SubscriptionPackageResponse> content = new ArrayList<>();
        for(String id : ids) {
            if(map.containsKey(id)) {
                content.add(map.get(id));
            }
        }

         long total = queryFactory
            .select(goi.countDistinct())
            .from(goi)
            .where(builder)
            .fetchOne();

        return new PageImpl<>(content, PageRequest.of(request.getPage(), request.getSize()), total);
    }
    
}
