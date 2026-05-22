package com.example.bookingclinic.adminclinic.repository.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.example.bookingclinic.adminclinic.dto.request.DoctorSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.DoctorResponse;
import com.example.bookingclinic.adminclinic.entity.QDoctorEntity;
import com.example.bookingclinic.adminclinic.repository.custom.DoctorRepositoryCustom;
import com.example.bookingclinic.adminclinic.repository.projection.DoctorProjection;
import com.example.bookingclinic.adminclinic.repository.projection.QDoctorProjection;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DoctorRepositoryImpl implements DoctorRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    
    @Override
    public List<DoctorResponse> search(DoctorSearchRequest request, String maPhongKham) {

        QDoctorEntity d = QDoctorEntity.doctorEntity;

        return queryFactory
            .select(Projections.constructor(DoctorResponse.class,
                d.maBacSi,
                d.tenBacSi,
                d.gioiTinh,
                d.soDienThoai,
                d.email,
                d.diaChi,
                d.specialty.tenChuyenKhoa,
                d.kinhNghiem,
                d.chucVu,
                d.hocHam,
                d.ngayDangKy
            ))
            .from(d)
            .where(
                d.clinic.maPhongKham.eq(maPhongKham),
                keywordContains(request.getTenBacSi(), d),
                phoneContains(request.getSoDienThoai(), d),
                addressContains(request.getDiaChi(), d),
                specialtyeq(request.getMaChuyenKhoa(), d),
                positionEq(request.getChucVu(), d),
                degreeEq(request.getHocHam(), d),
                registrationDateBetween(request.getFromDate(), request.getToDate(), d),
                d.isDeleted.eq(false)
            )
            .fetch();
    }

    private BooleanExpression keywordContains(String keyword, QDoctorEntity d){
        return (keyword == null || keyword.isEmpty())
            ? null
            : d.tenBacSi.containsIgnoreCase(keyword);
    }

    private BooleanExpression phoneContains(String phone, QDoctorEntity d){
        return (phone == null || phone.isEmpty())
            ? null
            : d.soDienThoai.contains(phone);
    }

    private BooleanExpression addressContains(String address, QDoctorEntity d){
        return (address == null || address.isEmpty())
            ? null
            : d.diaChi.containsIgnoreCase(address);
    }

    private BooleanExpression specialtyeq(String specialty, QDoctorEntity d){
        return (specialty == null || specialty.isEmpty())
            ? null
            : d.specialty.maChuyenKhoa.eq(specialty);
    }

    private BooleanExpression positionEq(String position, QDoctorEntity d){
        return (position == null || position.isEmpty())
            ? null
            : d.chucVu.eq(position);
    }

    private BooleanExpression degreeEq(String degree, QDoctorEntity d){
        return (degree == null || degree.isEmpty())
            ? null
            : d.hocHam.eq(degree);
    }

    private BooleanExpression registrationDateBetween(LocalDateTime from, LocalDateTime to, QDoctorEntity d){
        if(from != null && to != null) {
            return d.ngayDangKy.between(from, to);
        } else if (from != null) {
            return d.ngayDangKy.goe(from);
        } else if (to != null) {
            return d.ngayDangKy.loe(to);
        }
        return null;
    }

    @Override
    public DoctorProjection getDetail(String maBacSi) {

        QDoctorEntity d = QDoctorEntity.doctorEntity;

        return queryFactory
            .select(new QDoctorProjection(
                d.maBacSi, 
                d.tenBacSi,
                d.gioiTinh,
                d.ngaySinh,
                d.queQuan,
                d.soDienThoai,
                d.email,
                d.diaChi,
                d.avt,
                d.specialty.maChuyenKhoa,
                d.specialty.tenChuyenKhoa,
                d.bangCap,
                d.kinhNghiem,
                d.hoatDong,
                d.mieuTa1,
                d.mieuTa2,
                d.chucVu,
                d.hocHam,
                d.cccd,
                d.soGiayPhep,
                d.ngayCap,
                d.noiCap,
                d.clinic.maPhongKham,
                d.clinic.tenPhongKham,
                d.account.maTaiKhoan,
                d.account.soDt,
                d.account.matKhau,
                d.ngayDangKy,
                d.tepDinhKem
            ))
            .from(d)
            .where(d.maBacSi.eq(maBacSi))
            .fetchOne();
    }

    @Override
    public List<DoctorResponse> findAllDoctors(String maPhongKham) {
        QDoctorEntity d = QDoctorEntity.doctorEntity;

        return queryFactory
            .select(Projections.constructor(DoctorResponse.class,
                d.maBacSi,
                d.tenBacSi,
                d.gioiTinh,
                d.soDienThoai,
                d.email,        
                d.diaChi,
                d.specialty.tenChuyenKhoa,
                d.kinhNghiem,   
                d.chucVu,
                d.hocHam,
                d.ngayDangKy
            ))
            .from(d)
            .where(
                d.isDeleted.eq(false),
                d.clinic.maPhongKham.eq(maPhongKham)
            ) 
            .fetch();
    }
    
}
