package com.example.bookingclinic.adminsystem.repository.impl;

import java.util.List;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.entity.BrowseClinicEntity;
import com.example.bookingclinic.adminsystem.entity.QBrowseClinicEntity;
import com.example.bookingclinic.adminsystem.repository.custom.BrowseClinicRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrowseClinicRepositoryImpl implements BrowseClinicRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BrowseClinicEntity> searchBrowseClinic(BrowseClinicSearchRequest request) {

        QBrowseClinicEntity browseClinic = QBrowseClinicEntity.browseClinicEntity;

        BooleanBuilder builder = new BooleanBuilder();
        
        if(request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            String keyword = request.getKeyword();

            builder.and(
                browseClinic.tenPhongKham.containsIgnoreCase(keyword)
                .or(browseClinic.nguoiDaiDien.containsIgnoreCase(keyword))
                .or(browseClinic.tinhThanhPho.containsIgnoreCase(keyword))
                .or(browseClinic.diaChi.containsIgnoreCase(keyword))
            );
        }

        return queryFactory
            .selectFrom(browseClinic)
            .where(builder)
            .fetch();
    }
    
}
