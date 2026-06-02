package com.example.bookingclinic.adminclinic.repository.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.bookingclinic.adminclinic.dto.request.NotificationSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.NotificationIsReadResponse;
import com.example.bookingclinic.adminclinic.dto.response.NotificationResponse;
// import com.example.bookingclinic.adminclinic.entity.QAccountEntity;
import com.example.bookingclinic.adminclinic.entity.QNotificationAccountEntity;
import com.example.bookingclinic.adminclinic.entity.QNotificationEntity;
import com.example.bookingclinic.adminclinic.repository.custom.NotificationRepositoryCustom;
import com.example.bookingclinic.adminclinic.repository.projection.NotificationProjection;
import com.example.bookingclinic.adminclinic.repository.projection.QNotificationProjection;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<NotificationResponse> searchSentNotifications(NotificationSearchRequest request) {

    QNotificationEntity n = QNotificationEntity.notificationEntity;
    // QNotificationAccountEntity na = QNotificationAccountEntity.notificationAccountEntity;
    // QAccountEntity a = QAccountEntity.accountEntity;

    BooleanBuilder builder = new BooleanBuilder();
    builder.and(n.isDeleted.eq(false));
    builder.and(n.account.maTaiKhoan.eq(request.getMaTaiKhoan()));

    if(request.getKeyword() != null && !request.getKeyword().isEmpty()){
        builder.and(
            n.tieuDe.contains(request.getKeyword())
            .or(n.noiDung.contains(request.getKeyword()))
        );
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

    String sortDir = request.getSortDirection() == null ? "desc" : request.getSortDirection();
    Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
    // com.querydsl.core.types.OrderSpecifier<?> orderSpecifier = sortDir.equalsIgnoreCase("desc") 
    var orderSpecifier = sortDir.equalsIgnoreCase("desc")
        ? n.thoiGianGui.desc() 
        : n.thoiGianGui.asc();

    List<NotificationResponse> content = queryFactory
        .select(Projections.constructor(NotificationResponse.class,
            n.maThongBao,
            n.account.maTaiKhoan,
            n.tieuDe,
            n.noiDung,
            n.loaiThongBao,
            n.doiTuongNhan,
            n.thoiGianGui,
            n.files,
            n.anhThongBao,
            Expressions.constant(true)
        ))
        .from(n)
        .where(builder)
        .orderBy(orderSpecifier)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    // if(request.getMaTaiKhoan() != null && !request.getMaTaiKhoan().isEmpty()) {
    //     String currentUserId = request.getMaTaiKhoan();
    //     BooleanBuilder notificationCondition = new BooleanBuilder();
    //     notificationCondition.or(n.account.maTaiKhoan.eq(currentUserId));

    //     BooleanBuilder receivedCondition = new BooleanBuilder();
    //     receivedCondition.and(na.account.maTaiKhoan.eq(currentUserId));

    //     if(request.getIsRead() != null) {
    //         receivedCondition.and(na.isRead.eq(request.getIsRead()));
    //     }
    //     notificationCondition.or(receivedCondition);

    //     builder.and(notificationCondition);
    // }
    Long total = queryFactory
        .select(n.count())
        .from(n)
        .where(builder)
        .fetchOne();
    
    if(total == null) total = 0L;

    return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<NotificationResponse> searchReceivedNotifications(NotificationSearchRequest request) {

    QNotificationEntity n = QNotificationEntity.notificationEntity;
    QNotificationAccountEntity na = QNotificationAccountEntity.notificationAccountEntity;

    BooleanBuilder builder = new BooleanBuilder();

    builder.and(n.isDeleted.eq(false));

    // CHỈ lấy thông báo mình nhận được
    builder.and(
        na.account.maTaiKhoan.eq(request.getMaTaiKhoan())
    );

    if (request.getIsRead() != null) {
        builder.and(na.isRead.eq(request.getIsRead()));
    }

    Pageable pageable = PageRequest.of(
        request.getPage(),
        request.getSize()
    );

    List<NotificationResponse> content = queryFactory
        .select(Projections.constructor(
            NotificationResponse.class,
            n.maThongBao,
            n.account.maTaiKhoan,
            n.tieuDe,
            n.noiDung,
            n.loaiThongBao,
            n.doiTuongNhan,
            n.thoiGianGui,
            n.files,
            n.anhThongBao,
            na.isRead
        ))
        .from(na)
        .join(na.notification, n)
        .where(builder)
        .orderBy(n.thoiGianGui.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = queryFactory
        .select(na.count())
        .from(na)
        .join(na.notification, n)
        .where(builder)
        .fetchOne();

    return new PageImpl<>(
        content,
        pageable,
        total == null ? 0 : total
    );
    }

    @Override
    public NotificationProjection getDetail(String maThongBao) {
        QNotificationEntity n = QNotificationEntity.notificationEntity;
        QNotificationAccountEntity na = QNotificationAccountEntity.notificationAccountEntity;

        NotificationProjection projection = queryFactory
            .select( new QNotificationProjection(
                n.maThongBao,
                n.account.maTaiKhoan,
                n.account.soDt,
                n.account.hoVaTen,
                n.tieuDe,
                n.noiDung,
                n.loaiThongBao,
                n.doiTuongNhan,
                Expressions.nullExpression(),
                n.thoiGianGui,
                n.files,
                n.anhThongBao
            ))
            .from(n)
            .where(n.isDeleted.eq(false).and(n.maThongBao.eq(maThongBao)))
            .fetchFirst();

        if(projection != null) {
            List<NotificationIsReadResponse> danhSachNguoiNhan = queryFactory
                .select(Projections.constructor(NotificationIsReadResponse.class, 
                    na.account.maTaiKhoan,
                    na.account.hoVaTen,
                    na.isRead
                ))
                .from(na)
                .where(na.id.maThongBao.eq(maThongBao))
                .fetch();
            projection.setDanhSachNguoiNhan(danhSachNguoiNhan);
        }
        return projection;
    }
}
