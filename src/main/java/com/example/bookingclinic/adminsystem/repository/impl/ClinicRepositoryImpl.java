package com.example.bookingclinic.adminsystem.repository.impl;

import java.util.List;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.entity.ClinicEntity;
import com.example.bookingclinic.adminsystem.entity.QClinicEntity;
import com.example.bookingclinic.adminsystem.repository.custom.ClinicRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClinicRepositoryImpl implements ClinicRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ClinicEntity> searchClinic(BrowseClinicSearchRequest request) {

        QClinicEntity clinic = QClinicEntity.clinicEntity;

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(clinic.isDeleted.isFalse());
        
        if(request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            String keyword = request.getKeyword();

            builder.and(
                clinic.tenPhongKham.containsIgnoreCase(keyword)
                .or(clinic.nguoiDaiDien.containsIgnoreCase(keyword))
                .or(clinic.tinhThanhPho.containsIgnoreCase(keyword))
                .or(clinic.diaChi.containsIgnoreCase(keyword))
            );
        }

        return queryFactory
            .selectFrom(clinic)
            .where(builder)
            .fetch();
    }
    
}
