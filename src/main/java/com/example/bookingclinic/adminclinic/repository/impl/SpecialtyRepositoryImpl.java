package com.example.bookingclinic.adminclinic.repository.impl;

import java.util.List;
import java.util.Optional;

import com.example.bookingclinic.adminclinic.dto.request.SpecialtySearchRequest;
import com.example.bookingclinic.adminclinic.entity.QClinicEntity;
import com.example.bookingclinic.adminclinic.entity.QSpecialtyClinicEntity;
import com.example.bookingclinic.adminclinic.entity.QSpecialtyEntity;
import com.example.bookingclinic.adminclinic.repository.custom.SpecialtyRepositoryCustom;
import com.example.bookingclinic.adminclinic.repository.projection.QSpecialtyProjection;
import com.example.bookingclinic.adminclinic.repository.projection.SpecialtyProjection;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpecialtyRepositoryImpl implements SpecialtyRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QSpecialtyEntity s = QSpecialtyEntity.specialtyEntity;
    private final QSpecialtyClinicEntity sc = QSpecialtyClinicEntity.specialtyClinicEntity;
    private final QClinicEntity c = QClinicEntity.clinicEntity;

    @Override
    public Optional<SpecialtyProjection> findDetailByIdAndClinicId(String maPhongKham, String maChuyenKhoa) {

        SpecialtyProjection result = queryFactory
            .select(new QSpecialtyProjection(
                s.maChuyenKhoa,
                s.tenChuyenKhoa,
                c.maPhongKham,
                c.tenPhongKham,
                s.moTa,
                s.trangThai,
                s.ngayTao
            ))
            .from(sc)
            .join(sc.specialty, s)
            .join(sc.clinic, c)
            .where(
                s.maChuyenKhoa.eq(maChuyenKhoa)
                .and(c.maPhongKham.eq(maPhongKham))
                .and(s.isDeleted.eq(false))
            )
            .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<SpecialtyProjection> search(SpecialtySearchRequest request, String maPhongKham) {
        QSpecialtyEntity s = QSpecialtyEntity.specialtyEntity;

        BooleanBuilder builder = new BooleanBuilder();

        if(maPhongKham != null && !maPhongKham.trim().isEmpty()){
            builder.and(c.maPhongKham.eq(maPhongKham));
        }
        builder.and(s.isDeleted.eq(false));

        if(request.getKeyword() != null && !request.getKeyword().trim().isEmpty()) {
            builder.and(s.tenChuyenKhoa.containsIgnoreCase(request.getKeyword()));
        }

        if(request.getFromDate() != null && request.getToDate() != null){
        builder.and(s.ngayTao.between(request.getFromDate(), request.getToDate()));
        } else if(request.getFromDate() != null){
        builder.and(s.ngayTao.goe(request.getFromDate()));
        } else if(request.getToDate() != null){
        builder.and(s.ngayTao.loe(request.getToDate()));
       }

        return queryFactory
            .select(new QSpecialtyProjection(
                s.maChuyenKhoa,
                s.tenChuyenKhoa,
                c.maPhongKham,
                c.tenPhongKham,
                s.moTa,
                s.trangThai,
                s.ngayTao
            ))
            .from(sc)
            .join(sc.specialty, s)
            .join(sc.clinic, c)
            .where(builder)
            .orderBy(s.ngayTao.desc())
            .fetch();
    }

    @Override
    public List<SpecialtyProjection> findAllSpecialtyByClinicId(String maPhongKham) {
        QSpecialtyEntity s = QSpecialtyEntity.specialtyEntity;

        return queryFactory
                .select(new QSpecialtyProjection(
                    s.maChuyenKhoa,
                    s.tenChuyenKhoa,  
                    c.maPhongKham, 
                    c.tenPhongKham,
                    s.moTa, 
                    s.trangThai, 
                    s.ngayTao
                ))
                .from(sc)
                .join(sc.specialty, s)
                .join(sc.clinic, c)
                .where(
                        c.maPhongKham.eq(maPhongKham)
                        .and(s.isDeleted.eq(false))
                )
                .orderBy(s.ngayTao.desc())
                .fetch();
    }
    
}
