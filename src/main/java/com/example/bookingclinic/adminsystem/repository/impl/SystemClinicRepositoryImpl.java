package com.example.bookingclinic.adminsystem.repository.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminclinic.entity.ClinicEntity;
import com.example.bookingclinic.adminclinic.entity.QClinicEntity;
import com.example.bookingclinic.adminsystem.repository.custom.ClinicRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SystemClinicRepositoryImpl implements ClinicRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ClinicEntity> searchClinic(BrowseClinicSearchRequest request, Pageable pageable) {

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
        if(request.getTrangThai() != null && !request.getTrangThai().trim().isEmpty()) {
            builder.and(clinic.trangThai.equalsIgnoreCase(request.getTrangThai().trim()));
        }

        if(request.getLoaiHinhPhongKham() != null && !request.getLoaiHinhPhongKham().trim().isEmpty()) {
            builder.and(clinic.loaiHinhPhongKham.equalsIgnoreCase(request.getLoaiHinhPhongKham().trim()));
        }
        if(request.getTinhThanhPho() != null && !request.getTinhThanhPho().trim().isEmpty()) {
            builder.and(clinic.tinhThanhPho.equalsIgnoreCase(request.getTinhThanhPho().trim()));
        }
        
        List<ClinicEntity> results = queryFactory
            .selectFrom(clinic)
            .where(builder)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
        
        Long totalCount = queryFactory
            .select(clinic.count())
            .from(clinic)
            .where(builder)
            .fetchOne();
        
        Long total = (totalCount != null) ? totalCount :0L;
        return new PageImpl<>(results, pageable, total);
    }
    
}
