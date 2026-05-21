package com.example.bookingclinic.adminsystem.repository.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
    public Page<BrowseClinicEntity> searchBrowseClinic(BrowseClinicSearchRequest request, Pageable pageable) {

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
        if(request.getTrangThai() != null && !request.getTrangThai().isEmpty()){
            builder.and(browseClinic.trangThai.eq(request.getTrangThai()));
        }
        List<BrowseClinicEntity> results = queryFactory
            .selectFrom(browseClinic)
            .where(builder)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
        
        Long totalCount = queryFactory
            .select(browseClinic.count())
            .from(browseClinic)
            .where(builder)
            .fetchOne();
    
        Long total = (totalCount != null) ? totalCount : 0l;

        return new PageImpl<>(results, pageable, total);
    }
    
}
