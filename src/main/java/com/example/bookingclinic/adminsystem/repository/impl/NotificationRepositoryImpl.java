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
import com.querydsl.core.types.dsl.Expressions;

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

    boolean needJoinAccount = request.getMaTaiKhoan() != null || request.getIsRead() != null;

    var query = queryFactory
        .select(new QNotificationProjection (
            n.maThongBao,
            n.maTaiKhoan,
            needJoinAccount ? a.soDt : Expressions.nullExpression(),
            needJoinAccount ? a.hoVaTen : Expressions.nullExpression(),
            n.tieuDe,
            n.noiDung,
            n.loaiThongBao,
            n.doiTuongNhan,
            Expressions.nullExpression(),
            needJoinAccount ? na.isRead : Expressions.nullExpression(),
            n.thoiGianGui,
            n.files,
            n.anhThongBao
        ))
        .from(n);
    if(needJoinAccount) {
        query.leftJoin(na).on(n.maThongBao.eq(na.id.maThongBao))
             .leftJoin(a).on(a.maTaiKhoan.eq(na.id.maTaiKhoan));
        if(request.getMaTaiKhoan() != null) {
            builder.and(na.id.maTaiKhoan.eq(request.getMaTaiKhoan()).or(n.maTaiKhoan.eq(request.getMaTaiKhoan())));
        }
        if(request.getIsRead() != null) {
            builder.and(na.isRead.eq(request.getIsRead()));
        }
    }
    String sortDir = request.getSortDirection() == null ? "desc" : request.getSortDirection();
    Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
    com.querydsl.core.types.OrderSpecifier<?> orderSpecifier = sortDir.equalsIgnoreCase("desc") 
        ? n.thoiGianGui.desc() 
        : n.thoiGianGui.asc();
    
    List<NotificationProjection> content = query
        .where(builder)
        .distinct()
        .orderBy(orderSpecifier)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = queryFactory
        .select(n.maThongBao.countDistinct())
        .from(n)
        .where(builder)
        .fetchOne();
    
    if(total == null) total = 0L;

    return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<NotificationProjection> getAll() {
        QNotificationEntity n = QNotificationEntity.notificationEntity;
        QNotificationAccountEntity na = QNotificationAccountEntity.notificationAccountEntity;

        List<com.querydsl.core.Tuple> tuples = queryFactory
            .select(
                n.maThongBao,
                n.maTaiKhoan,
                n.tieuDe,
                n.noiDung,
                n.loaiThongBao,
                n.doiTuongNhan,
                n.thoiGianGui,
                n.files,
                n.anhThongBao,
                na.id.maTaiKhoan 
            )
            .from(n)
            .leftJoin(na).on(n.maThongBao.eq(na.id.maThongBao))
            .where(n.isDeleted.eq(false))
            .orderBy(n.thoiGianGui.desc())
            .fetch();

        java.util.Map<String, NotificationProjection> map = new java.util.LinkedHashMap<>();

        for (com.querydsl.core.Tuple t : tuples) {
            String idThongBao = t.get(n.maThongBao);
            String idNguoiNhan = t.get(na.id.maTaiKhoan);

            NotificationProjection proj = map.get(idThongBao);
            if (proj == null) {
                proj = new NotificationProjection();
                proj.setMaThongBao(idThongBao);
                proj.setMaTaiKhoan(t.get(n.maTaiKhoan));
                proj.setTieuDe(t.get(n.tieuDe));
                proj.setNoiDung(t.get(n.noiDung));
                proj.setLoaiThongBao(t.get(n.loaiThongBao));
                proj.setDoiTuongNhan(t.get(n.doiTuongNhan));
                proj.setThoiGianGui(t.get(n.thoiGianGui));
                proj.setFiles(t.get(n.files));
                proj.setAnhThongBao(t.get(n.anhThongBao));
                
                // Khởi tạo mảng rỗng
                proj.setDanhSachNguoiNhan(new java.util.ArrayList<>());
                
                map.put(idThongBao, proj);
            }

            if (idNguoiNhan != null) {
                proj.getDanhSachNguoiNhan().add(idNguoiNhan);
            }
        }

        return new java.util.ArrayList<>(map.values());
    }

    @Override
    public NotificationProjection getDetail(String maThongBao) {
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
                Expressions.nullExpression(),
                Expressions.nullExpression(),
                n.thoiGianGui,
                n.files,
                n.anhThongBao
            ))
            .from(n)
            .join(a).on(a.maTaiKhoan.eq(n.maTaiKhoan))
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
