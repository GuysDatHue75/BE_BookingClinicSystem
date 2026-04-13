package com.example.bookingclinic.adminsystem.repository.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.bookingclinic.adminsystem.dto.request.NotificationSearchRequest;
import com.example.bookingclinic.adminsystem.entity.QAccountEntity;
import com.example.bookingclinic.adminsystem.entity.QNotificationAccountEntity;
import com.example.bookingclinic.adminsystem.entity.QNotificationEntity;
import com.example.bookingclinic.adminsystem.repository.custom.NotificationRepositoryCustom;
import com.example.bookingclinic.adminsystem.repository.projection.NotificationProjection;
import com.example.bookingclinic.adminsystem.repository.projection.QNotificationProjection;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<NotificationProjection> search(NotificationSearchRequest request) {

    QNotificationEntity n = QNotificationEntity.notificationEntity;
    QNotificationAccountEntity na = QNotificationAccountEntity.notificationAccountEntity;
    QAccountEntity a = QAccountEntity.accountEntity;

    BooleanBuilder builder = new BooleanBuilder();

    builder.and(na.id.maTaiKhoan.eq(request.getMaTaiKhoan()).or(n.maTaiKhoan.eq(request.getMaTaiKhoan())));

    if(request.getKeyword() != null && !request.getKeyword().isEmpty()){
        builder.and(
            n.tieuDe.containsIgnoreCase(request.getKeyword())
            .or(n.noiDung.containsIgnoreCase(request.getKeyword()))
        );
    }

    if(request.getIsRead() != null){
        builder.and(na.isRead.eq(request.getIsRead()));
    }

    if(request.getLoaiThongBao() != null && !request.getLoaiThongBao().isEmpty()){
        builder.and(n.loaiThongBao.equalsIgnoreCase(request.getLoaiThongBao()));
    }

    if(request.getDoiTuongNhan() != null && !request.getDoiTuongNhan().isEmpty()){
        builder.and(n.doiTuongNhan.equalsIgnoreCase(request.getDoiTuongNhan()));
    }

    if(request.getFromDate() != null && request.getToDate() != null){
        builder.and(n.thoiGianGui.between(request.getFromDate(), request.getToDate()));
    } else if(request.getFromDate() != null){
        builder.and(n.thoiGianGui.goe(request.getFromDate()));
    } else if(request.getToDate() != null){
        builder.and(n.thoiGianGui.loe(request.getToDate()));
    }

    Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
    
    List<NotificationProjection> content = queryFactory
        .select( new QNotificationProjection(
            n.maThongBao,
            n.maTaiKhoan,
            a.soDt,
            a.hoVaTen,
            n.tieuDe,
            n.noiDung,
            n.loaiThongBao,
            n.doiTuongNhan,
            null,
            na.isRead,
            n.thoiGianGui
        ))
        .from(n)
        .join(na).on(n.maThongBao.eq(na.id.maThongBao))
        .join(a).on(a.maTaiKhoan.eq(na.id.maTaiKhoan))
        .where(builder)
        .orderBy(n.thoiGianGui.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = queryFactory
        .select(n.maThongBao.countDistinct())
        .from(n)
        .join(na).on(n.maThongBao.eq(na.id.maThongBao))
        .join(a).on(a.maTaiKhoan.eq(na.id.maTaiKhoan))
        .where(builder)
        .fetchOne();

    return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<NotificationProjection> getAll(String maTaiKhoan) {
        QNotificationEntity n = QNotificationEntity.notificationEntity;
        QNotificationAccountEntity na = QNotificationAccountEntity.notificationAccountEntity;
        QAccountEntity a = QAccountEntity.accountEntity;

        return queryFactory
            .select( new QNotificationProjection(
                n.maThongBao,
                n.maTaiKhoan,
                a.soDt,
                a.hoVaTen,
                n.tieuDe,
                n.noiDung,
                n.loaiThongBao,
                n.doiTuongNhan,
                null,
                na.isRead,
                n.thoiGianGui
            ))
            .from(n)
            .join(na).on(n.maThongBao.eq(na.id.maThongBao))
            .join(a).on(a.maTaiKhoan.eq(na.id.maTaiKhoan))
            .where(na.id.maTaiKhoan.eq(maTaiKhoan).or(n.maTaiKhoan.eq(maTaiKhoan)))
            .orderBy(n.thoiGianGui.desc())
            .fetch();
    }

    @Override
    public NotificationProjection getDetail(String maThongBao, String maTaiKhoan) {
        QNotificationEntity n = QNotificationEntity.notificationEntity;
        QNotificationAccountEntity na = QNotificationAccountEntity.notificationAccountEntity;
        QAccountEntity a = QAccountEntity.accountEntity;

        NotificationProjection projection = queryFactory
            .select( new QNotificationProjection(
                n.maThongBao,
                n.maTaiKhoan,
                a.soDt,
                a.hoVaTen,
                n.tieuDe,
                n.noiDung,
                n.loaiThongBao,
                n.doiTuongNhan,
                null,
                na.isRead,
                n.thoiGianGui
            ))
            .from(n)
            .join(na).on(n.maThongBao.eq(na.id.maThongBao))
            .join(a).on(a.maTaiKhoan.eq(na.id.maTaiKhoan))
            .where(n.maThongBao.eq(maThongBao).and(na.id.maTaiKhoan.eq(maTaiKhoan).or(n.maTaiKhoan.eq(maTaiKhoan))))
            .fetchOne();

        List<String> danhSachNguoiNhan = queryFactory
            .select(na.id.maTaiKhoan)
            .from(na)
            .where(na.id.maThongBao.eq(maThongBao))
            .fetch();
        projection.setDanhSachNguoiNhan(danhSachNguoiNhan);
        return projection;
    }
}
