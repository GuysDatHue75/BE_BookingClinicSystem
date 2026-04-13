package com.example.bookingclinic.adminsystem.repository.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.example.bookingclinic.adminsystem.dto.request.BrowseDoctorSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.BrowseDoctorDetailResponse;
import com.example.bookingclinic.adminsystem.dto.response.QBrowseDoctorDetailResponse;
import com.example.bookingclinic.adminsystem.entity.QBrowseDoctorEntity;
import com.example.bookingclinic.adminsystem.entity.QClinicEntity;
import com.example.bookingclinic.adminsystem.repository.custom.BrowseDoctorRepositoryCustom;
import com.example.bookingclinic.adminsystem.repository.projection.BrowseDoctorProjection;
import com.example.bookingclinic.adminsystem.repository.projection.QBrowseDoctorProjection;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrowseDoctorRepositoryImpl  implements BrowseDoctorRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    
    @Override
    public List<BrowseDoctorProjection> search(BrowseDoctorSearchRequest request){

        QBrowseDoctorEntity d = QBrowseDoctorEntity.browseDoctorEntity;
        QClinicEntity c = QClinicEntity.clinicEntity;

        return queryFactory
            .select(new QBrowseDoctorProjection(
                d.maBacSi,
                d.tenBacSi,
                d.gioiTinh,
                d.soDienThoai,
                d.diaChi,
                c.tenPhongKham,
                d.chucVu,
                d.hocHam,
                d.trangThai,
                d.ngayDangKy
            ))
            .from(d)
            .leftJoin(c).on(d.maPhongKham.eq(c.maPhongKham))
            .where(
                keywordContains(request.getKeyword(), d),
                phoneContains(request.getSoDienThoai(), d),
                addressContains(request.getDiaChi(), d),
                specialtyeq(request.getChuyenKhoa(), d),
                positionEq(request.getChucVu(), d),
                degreeEq(request.getHocHam(), d),
                statusEq(request.getTrangThai(), d),
                registrationDateBetween(request.getFromDate(), request.getToDate(), d)
            )
            .fetch();
    }

    private BooleanExpression keywordContains(String keyword, QBrowseDoctorEntity d){
        return (keyword == null || keyword.isEmpty())
            ? null
            : d.tenBacSi.containsIgnoreCase(keyword);
    }

    private BooleanExpression phoneContains(String phone, QBrowseDoctorEntity d){
        return (phone == null || phone.isEmpty())
            ? null
            : d.soDienThoai.contains(phone);
    }

    private BooleanExpression addressContains(String address, QBrowseDoctorEntity d){
        return (address == null || address.isEmpty())
            ? null
            : d.diaChi.containsIgnoreCase(address);
    }

    private BooleanExpression specialtyeq(String specialty, QBrowseDoctorEntity d){
        return (specialty == null || specialty.isEmpty())
            ? null
            : d.chuyenKhoa.eq(specialty);
    }

    private BooleanExpression positionEq(String position, QBrowseDoctorEntity d){
        return (position == null || position.isEmpty())
            ? null
            : d.chucVu.eq(position);
    }

    private BooleanExpression degreeEq(String degree, QBrowseDoctorEntity d){
        return (degree == null || degree.isEmpty())
            ? null
            : d.hocHam.eq(degree);
    }

    private BooleanExpression statusEq(String status, QBrowseDoctorEntity d){
        return (status == null || status.isEmpty())
            ? null
            : d.trangThai.eq(status);
    }

    private BooleanExpression registrationDateBetween(LocalDateTime from, LocalDateTime to, QBrowseDoctorEntity d){
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
    public BrowseDoctorDetailResponse getDetail(String maBacSi) {

        QBrowseDoctorEntity d = QBrowseDoctorEntity.browseDoctorEntity;
        QClinicEntity c = QClinicEntity.clinicEntity;

        return queryFactory
            .select(new QBrowseDoctorDetailResponse(
                d.maBacSi, 
                d.tenBacSi,
                d.gioiTinh,
                d.soDienThoai,
                d.email,
                d.diaChi,
                d.avt,
                d.chuyenKhoa,
                d.bangCap,
                d.kinhNghiem,
                d.hoatDong,
                d.mieuTa,
                d.chucVu,
                d.hocHam,
                d.cccd,
                d.soGiayPhep,
                d.ngayCap,
                d.noiCap,
                d.trangThai,
                d.maPhongKham,
                c.tenPhongKham,
                d.ngayDangKy,
                d.tepDinhKem
            ))
            .from(d)
            .join(c).on(d.maPhongKham.eq(c.maPhongKham))
            .where(d.maBacSi.eq(maBacSi))
            .fetchOne();
    }
}