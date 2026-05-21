package com.example.bookingclinic.adminsystem.repository.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.bookingclinic.adminsystem.dto.request.NotificationSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.NotificationResponse;
import com.example.bookingclinic.adminsystem.entity.QAccountEntity;
import com.example.bookingclinic.adminsystem.entity.QNotificationAccountEntity;
import com.example.bookingclinic.adminsystem.entity.QNotificationEntity;
import com.example.bookingclinic.adminsystem.repository.custom.NotificationRepositoryCustom;
import com.example.bookingclinic.adminsystem.repository.projection.NotificationProjection;
import com.example.bookingclinic.adminsystem.repository.projection.QNotificationProjection;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<NotificationResponse> search(NotificationSearchRequest request) {

    QNotificationEntity n = QNotificationEntity.notificationEntity;
    QNotificationAccountEntity na = QNotificationAccountEntity.notificationAccountEntity;

    BooleanBuilder builder = new BooleanBuilder();
    builder.and(n.isDeleted.eq(false));

    // if(request.getMaTaiKhoan() != null){
    //     builder.and(na.id.maTaiKhoan.eq(request.getMaTaiKhoan()).or(n.maTaiKhoan.eq(request.getMaTaiKhoan())));
    // }

    if(request.getKeyword() != null && !request.getKeyword().isEmpty()){
        String keyword = request.getKeyword();
        builder.and(
            n.tieuDe.contains(keyword)
            .or(n.noiDung.contains(keyword))
        );
    }

    if(request.getIsRead() != null){
        builder.and(na.isRead.eq(request.getIsRead()));
    }

    if(request.getLoaiThongBao() != null && !request.getLoaiThongBao().isEmpty()){
        builder.and(n.loaiThongBao.eq(request.getLoaiThongBao()));
    }

    if(request.getDoiTuongNhan() != null && !request.getDoiTuongNhan().isEmpty()){
        builder.and(n.doiTuongNhan.eq(request.getDoiTuongNhan()));
    }

    if(request.getFromDate() != null && request.getToDate() != null){
        builder.and(n.thoiGianGui.between(request.getFromDate(), request.getToDate()));
    } else if(request.getFromDate() != null){
        builder.and(n.thoiGianGui.goe(request.getFromDate()));
    } else if(request.getToDate() != null){
        builder.and(n.thoiGianGui.loe(request.getToDate()));
    }

    String currentUserId = request.getMaTaiKhoan();
    boolean needJoinAccount = request.getMaTaiKhoan() != null || request.getIsRead() != null;
    if(currentUserId != null){
        builder.and(na.id.maTaiKhoan.eq(currentUserId).or(n.account.maTaiKhoan.eq(currentUserId)));
    }

    var query = queryFactory
        .select(Projections.constructor(NotificationResponse.class,
            n.maThongBao,
            n.account.maTaiKhoan,
            n.tieuDe,
            n.noiDung,
            n.loaiThongBao,
            n.doiTuongNhan,
            n.thoiGianGui,
            n.files,
            n.anhThongBao
        ))
        .from(n);

    if(needJoinAccount) {
        query.leftJoin(na).on(n.maThongBao.eq(na.id.maThongBao));
    }

    String sortDir = request.getSortDirection() == null ? "desc" : request.getSortDirection();
    Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
    com.querydsl.core.types.OrderSpecifier<?> orderSpecifier = sortDir.equalsIgnoreCase("desc") 
        ? n.thoiGianGui.desc() 
        : n.thoiGianGui.asc();
    
    List<NotificationResponse> content = query
        .where(builder)
        .distinct()
        .orderBy(orderSpecifier)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    var countQuery = queryFactory
        .select(n.maThongBao.countDistinct())
        .from(n);
    if(needJoinAccount){
        countQuery.leftJoin(na).on(n.maThongBao.eq(na.id.maThongBao));
    }

    Long total = queryFactory
        .select(n.maThongBao.countDistinct())
        .from(n)
        .where(builder)
        .fetchOne();
    
    if(total == null) total = 0L;

    return new PageImpl<>(content, pageable, total);
    }

    @Override
    public NotificationProjection getDetail(String maThongBao) {
        QNotificationEntity n = QNotificationEntity.notificationEntity;
        QNotificationAccountEntity na = QNotificationAccountEntity.notificationAccountEntity;
        QAccountEntity a = QAccountEntity.accountEntity;

        NotificationProjection projection = queryFactory
            .select( new QNotificationProjection(
                n.maThongBao,
                n.account.maTaiKhoan,
                a.soDt,
                a.hoVaTen,
                n.tieuDe,
                n.noiDung,
                n.loaiThongBao,
                n.doiTuongNhan,
                Expressions.nullExpression(),
                Expressions.nullExpression(),
                Expressions.nullExpression(),
                n.thoiGianGui,
                n.files,
                n.anhThongBao
            ))
            .from(n)
            .join(a).on(a.maTaiKhoan.eq(n.account.maTaiKhoan))
            .where(n.isDeleted.eq(false).and(n.maThongBao.eq(maThongBao)))
            .fetchFirst();

        if(projection != null) {
            List<String> danhSachNguoiNhan = queryFactory
                .select(na.id.maTaiKhoan)
                .from(na)
                .where(na.id.maThongBao.eq(maThongBao))
                .fetch();
            projection.setDanhSachNguoiNhan(danhSachNguoiNhan);
        }
        return projection;
    }
}
