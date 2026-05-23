package com.example.bookingclinic.adminclinic.repository.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.bookingclinic.adminclinic.dto.request.NewsSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.NewsResponse;
import com.example.bookingclinic.adminclinic.entity.QNewsEntity;
import com.example.bookingclinic.adminclinic.repository.custom.NewsRepositoryCustom;
import com.example.bookingclinic.adminclinic.repository.projection.NewsDetailProjection;
import com.example.bookingclinic.adminclinic.repository.projection.QNewsDetailProjection;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NewsRepositoryImpl implements NewsRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<NewsResponse> search(NewsSearchRequest request, String maPhongKham){
        QNewsEntity n = QNewsEntity.newsEntity;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(n.isDeleted.eq(false));

        if (maPhongKham != null && !maPhongKham.isEmpty()) {
            builder.and(n.clinic.maPhongKham.eq(maPhongKham));
        }

        if(request.getKeyword() != null && !request.getKeyword().isEmpty()){
            String keyword = request.getKeyword();
            builder.and(
                n.tieuDe.contains(keyword)
                .or(n.noiDung.contains(keyword))
            );
        }

        if(request.getFromDate() != null && request.getToDate() != null){
            builder.and(n.ngayCapNhat.between(request.getFromDate(), request.getToDate()));
        } else if(request.getFromDate() != null){
            builder.and(n.ngayCapNhat.goe(request.getFromDate()));
        } else if(request.getToDate() != null){
            builder.and(n.ngayCapNhat.loe(request.getToDate()));
        }

        var query = queryFactory
            .select(Projections.constructor(NewsResponse.class,
                n.maTinTuc,
                n.tieuDe,
                n.moTaNgan,
                n.noiDung,
                n.ngayTao,
                n.ngayCapNhat
            ))
            .from(n);
        
        String sortDir = request.getSortDirection() == null ? "desc" : request.getSortDirection();
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        com.querydsl.core.types.OrderSpecifier<?> orderSpecifier = sortDir.equalsIgnoreCase("desc") 
            ? n.ngayTao.desc() 
            : n.ngayTao.asc();

        List<NewsResponse> content = query
            .where(builder)
            .distinct()
            .orderBy(orderSpecifier)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(n.maTinTuc.countDistinct())
            .from(n)
            .where(builder)
            .fetchOne();
    
    if(total == null) total = 0L;

    return new PageImpl<>(content, pageable, total);

    }

    @Override
    public NewsDetailProjection getDetail(String maTinTuc, String maPhongKham){
        QNewsEntity n = QNewsEntity.newsEntity;

        NewsDetailProjection news = queryFactory
            .select(new QNewsDetailProjection(
                n.maTinTuc,
                n.tieuDe,
                n.moTaNgan,
                n.noiDung,
                n.ngayTao,
                n.ngayCapNhat,
                n.anh,
                n.clinic.tenPhongKham
            ))
            .from(n)
            .where(n.isDeleted.eq(false).and(n.maTinTuc.eq(maTinTuc)).and(n.clinic.maPhongKham.eq(maPhongKham)))
            .fetchFirst();
        return news;
    }

}
