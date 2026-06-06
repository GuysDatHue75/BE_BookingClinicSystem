package com.example.bookingclinic.adminsystem.repository.impl;

import java.util.List;

import com.example.bookingclinic.adminclinic.entity.QAccountEntity;
import com.example.bookingclinic.adminsystem.dto.request.AccountSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.AccountResponse;
import com.example.bookingclinic.adminsystem.dto.response.PageResponse;
import com.example.bookingclinic.adminsystem.repository.custom.AccountRepositoryCustom;
import com.example.bookingclinic.adminsystem.repository.projection.AccountProjection;
import com.example.bookingclinic.adminsystem.repository.projection.QAccountProjection;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SystemAccountRepositoryImpl implements AccountRepositoryCustom {
    private final JPAQueryFactory queryFactory;
 
    @Override
    public PageResponse<AccountResponse> search(AccountSearchRequest request) {
        QAccountEntity a = QAccountEntity.accountEntity;
        BooleanBuilder builder = new BooleanBuilder();
 
        builder.and(a.isDeleted.eq(false));
 
        if (request.getKeyword() != null && !request.getKeyword().trim().isEmpty()) {
            String kw = request.getKeyword().trim();
            builder.and(
                a.hoVaTen.containsIgnoreCase(kw)
                .or(a.soDt.contains(kw))
                .or(a.email.containsIgnoreCase(kw))
            );
        }
 
        if (request.getVaiTro() != null && !request.getVaiTro().trim().isEmpty()) {
            builder.and(a.vaiTro.eq(request.getVaiTro().trim()));
        }
 
        int page = Math.max(request.getPage(), 0);
        int size = (request.getSize() > 0) ? request.getSize() : 10;
        long offset = (long) page * size;
 
        long totalElements = queryFactory
            .select(a.count())
            .from(a)
            .where(builder)
            .fetchOne();
 
        List<AccountResponse> content = queryFactory
            .select(Projections.constructor(AccountResponse.class,
                a.maTaiKhoan,
                a.soDt,
                a.vaiTro,
                a.hoVaTen,
                a.email,
                // a.anhDaiDien,
                a.trangThai,
                a.ngayTao,
                a.ngayCapNhat
            ))
            .from(a)
            .where(builder)
            .orderBy(a.ngayTao.desc())
            .offset(offset)
            .limit(size)
            .fetch();
 
        int totalPages = (int) Math.ceil((double) totalElements / size);
 
        return PageResponse.<AccountResponse>builder()
            .content(content)
            .page(page)
            .size(size)
            .totalElements(totalElements)
            .totalPages(totalPages)
            .first(page == 0)
            .last(page >= totalPages - 1)
            .build();
    }
 
    @Override
    public AccountProjection getDetail(String maTaiKhoan) {
        QAccountEntity a = QAccountEntity.accountEntity;
 
        return queryFactory
            .select(new QAccountProjection(
                a.maTaiKhoan,
                a.soDt,
                a.vaiTro,
                a.hoVaTen,
                a.anhDaiDien,
                a.trangThai,
                a.ngayTao,
                a.ngayCapNhat,
                a.provider,
                a.email
            ))
            .from(a)
            .where(a.maTaiKhoan.eq(maTaiKhoan))
            .fetchOne();
    }
}
