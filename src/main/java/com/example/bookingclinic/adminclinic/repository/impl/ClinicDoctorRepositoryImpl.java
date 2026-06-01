package com.example.bookingclinic.adminclinic.repository.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.example.bookingclinic.adminclinic.dto.request.DoctorSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.DoctorResponse;
import com.example.bookingclinic.adminclinic.entity.QDoctorEntity;
import com.example.bookingclinic.adminclinic.repository.custom.DoctorRepositoryCustom;
import com.example.bookingclinic.adminclinic.repository.projection.DoctorProjection;
import com.example.bookingclinic.adminclinic.repository.projection.QDoctorProjection;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClinicDoctorRepositoryImpl implements DoctorRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    
    // @Override
    // public List<DoctorResponse> search(DoctorSearchRequest request, String maPhongKham) {

    //     QDoctorEntity d = QDoctorEntity.doctorEntity;

    //     return queryFactory
    //         .select(Projections.constructor(DoctorResponse.class,
    //             d.maBacSi,
    //             d.tenBacSi,
    //             d.gioiTinh,
    //             d.soDienThoai,
    //             d.email,
    //             d.diaChi,
    //             d.specialty.tenChuyenKhoa,
    //             d.kinhNghiem,
    //             d.chucVu,
    //             d.hocHam,
    //             d.ngayDangKy
    //         ))
    //         .from(d)
    //         .where(
    //             d.clinic.maPhongKham.eq(maPhongKham),
    //             d.isDeleted.eq(false),
    //             multiFieldKeywordContains(request.getKeyword(), d),
    //             specialtyeq(request.getMaChuyenKhoa(), d),
    //             positionEq(request.getChucVu(), d),
    //             degreeEq(request.getHocHam(), d),
    //             registrationDateBetween(request.getFromDate(), request.getToDate(), d)
    //         )
    //         .fetch();
    // }

    // private BooleanExpression multiFieldKeywordContains(String keyword, QDoctorEntity d){
    //     if (keyword == null || keyword.trim().isEmpty()) {
    //         return null; 
    //     }
        
    //     // 1. Ép từ khóa về chữ thường ngay trên RAM của Java
    //     String keywordLower = keyword.trim().toLowerCase();
        
    //     // 2. Dùng .lower().like("%...%") thay vì .containsIgnoreCase() để né hoàn toàn lỗi SQM của Hibernate 6
    //     return d.tenBacSi.lower().like("%" + keywordLower + "%")
    //         .or(d.soDienThoai.lower().like("%" + keywordLower + "%"))
    //         .or(d.diaChi.lower().like("%" + keywordLower + "%"));
    // }

    // private BooleanExpression specialtyeq(String specialty, QDoctorEntity d){
    //     return (specialty == null || specialty.isEmpty())
    //         ? null
    //         : d.specialty.maChuyenKhoa.eq(specialty);
    // }

    // private BooleanExpression positionEq(String position, QDoctorEntity d){
    //     return (position == null || position.isEmpty())
    //         ? null
    //         : d.chucVu.eq(position);
    // }

    // private BooleanExpression degreeEq(String degree, QDoctorEntity d){
    //     return (degree == null || degree.isEmpty())
    //         ? null
    //         : d.hocHam.eq(degree);
    // }

    // private BooleanExpression registrationDateBetween(LocalDateTime from, LocalDateTime to, QDoctorEntity d){
    //     if(from != null && to != null) {
    //         return d.ngayDangKy.between(from, to);
    //     } else if (from != null) {
    //         return d.ngayDangKy.goe(from);
    //     } else if (to != null) {
    //         return d.ngayDangKy.loe(to);
    //     }
    //     return null;
    // }
    @Override
    public List<DoctorResponse> search(DoctorSearchRequest request, String maPhongKham) {

        QDoctorEntity d = QDoctorEntity.doctorEntity;
        BooleanBuilder builder = new BooleanBuilder();

        // 1. Các điều kiện bắt buộc
        builder.and(d.clinic.maPhongKham.eq(maPhongKham));
        builder.and(d.isDeleted.eq(false));

        // 2. Tìm kiếm đa trường: Tên HOẶC SĐT HOẶC Địa chỉ
        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            builder.and(
                d.tenBacSi.contains(request.getKeyword())
                .or(d.soDienThoai.contains(request.getKeyword()))
                .or(d.diaChi.contains(request.getKeyword()))
            );
        }

        // 3. Lọc theo chuyên khoa
        if (request.getMaChuyenKhoa() != null && !request.getMaChuyenKhoa().isEmpty()) {
            builder.and(d.specialty.maChuyenKhoa.eq(request.getMaChuyenKhoa()));
        }

        // 4. Lọc theo chức vụ
        if (request.getChucVu() != null && !request.getChucVu().isEmpty()) {
            builder.and(d.chucVu.eq(request.getChucVu()));
        }

        // 5. Lọc theo học hàm
        if (request.getHocHam() != null && !request.getHocHam().isEmpty()) {
            builder.and(d.hocHam.eq(request.getHocHam()));
        }

        // 6. Lọc theo ngày đăng ký
        if (request.getFromDate() != null && request.getToDate() != null) {
            builder.and(d.ngayDangKy.between(request.getFromDate(), request.getToDate()));
        } else if (request.getFromDate() != null) {
            builder.and(d.ngayDangKy.goe(request.getFromDate()));
        } else if (request.getToDate() != null) {
            builder.and(d.ngayDangKy.loe(request.getToDate()));
        }

        // 7. Thực thi truy vấn với builder
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
            .where(builder) // Truyền thẳng cục BooleanBuilder vào đây
            .fetch();
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
