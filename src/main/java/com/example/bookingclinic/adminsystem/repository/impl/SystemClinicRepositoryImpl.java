// package com.example.bookingclinic.adminsystem.repository.impl;

// import java.util.List;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.Pageable;

// import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
// import com.example.bookingclinic.adminclinic.entity.ClinicEntity;
// import com.example.bookingclinic.adminclinic.entity.QClinicEntity;
// import com.example.bookingclinic.adminsystem.repository.custom.ClinicRepositoryCustom;
// import com.querydsl.core.BooleanBuilder;
// import com.querydsl.jpa.impl.JPAQueryFactory;

// import lombok.RequiredArgsConstructor;

// @RequiredArgsConstructor
// public class SystemClinicRepositoryImpl implements ClinicRepositoryCustom {
    
//     private final JPAQueryFactory queryFactory;

//     @Override
//     public Page<ClinicEntity> searchClinic(BrowseClinicSearchRequest request, Pageable pageable) {

//         QClinicEntity clinic = QClinicEntity.clinicEntity;

//         BooleanBuilder builder = new BooleanBuilder();

//         builder.and(clinic.isDeleted.isFalse());
        
//         if(request.getKeyword() != null && !request.getKeyword().isEmpty()) {
//             String keyword = request.getKeyword();

//             builder.and(
//                 clinic.tenPhongKham.containsIgnoreCase(keyword)
//                 .or(clinic.nguoiDaiDien.containsIgnoreCase(keyword))
//                 .or(clinic.tinhThanhPho.containsIgnoreCase(keyword))
//                 .or(clinic.diaChi.containsIgnoreCase(keyword))
//             );
//         }
//         if(request.getTrangThai() != null && !request.getTrangThai().trim().isEmpty()) {
//             builder.and(clinic.trangThai.equalsIgnoreCase(request.getTrangThai().trim()));
//         }

//         if(request.getLoaiHinhPhongKham() != null && !request.getLoaiHinhPhongKham().trim().isEmpty()) {
//             builder.and(clinic.loaiHinhPhongKham.equalsIgnoreCase(request.getLoaiHinhPhongKham().trim()));
//         }
//         if(request.getTinhThanhPho() != null && !request.getTinhThanhPho().trim().isEmpty()) {
//             builder.and(clinic.tinhThanhPho.equalsIgnoreCase(request.getTinhThanhPho().trim()));
//         }
        
//         List<ClinicEntity> results = queryFactory
//             .selectFrom(clinic)
//             .where(builder)
//             .offset(pageable.getOffset())
//             .limit(pageable.getPageSize())
//             .fetch();
        
//         Long totalCount = queryFactory
//             .select(clinic.count())
//             .from(clinic)
//             .where(builder)
//             .fetchOne();
        
//         Long total = (totalCount != null) ? totalCount :0L;
//         return new PageImpl<>(results, pageable, total);
//     }
    
// }
package com.example.bookingclinic.adminsystem.repository.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminclinic.entity.ClinicEntity;
import com.example.bookingclinic.adminclinic.entity.QClinicEntity;
import com.example.bookingclinic.adminsystem.repository.custom.ClinicRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SystemClinicRepositoryImpl implements ClinicRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ClinicEntity> searchClinic(BrowseClinicSearchRequest request, Pageable pageable) {

        QClinicEntity clinic = QClinicEntity.clinicEntity;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(clinic.isDeleted.isFalse());
        
        // 1. CHUẨN HÓA KEYWORD TÌM KIẾM ĐA TRƯỜNG (Né lỗi SQM Hibernate 6)
        if(request.getKeyword() != null && !request.getKeyword().trim().isEmpty()) {
            String keyword = request.getKeyword().trim().toLowerCase();

            builder.and(
                clinic.tenPhongKham.lower().like("%" + keyword + "%")
                .or(clinic.nguoiDaiDien.lower().like("%" + keyword + "%"))
                .or(clinic.tinhThanhPho.lower().like("%" + keyword + "%"))
                .or(clinic.diaChi.lower().like("%" + keyword + "%"))
            );
        }

        if(request.getTrangThai() != null && !request.getTrangThai().trim().isEmpty()) {
            builder.and(clinic.trangThai.equalsIgnoreCase(request.getTrangThai().trim()));
        }

        if(request.getLoaiHinhPhongKham() != null && !request.getLoaiHinhPhongKham().trim().isEmpty()) {
            builder.and(clinic.loaiHinhPhongKham.equalsIgnoreCase(request.getLoaiHinhPhongKham().trim()));
        }

        // 2. CHUẨN HÓA TÌM KIẾM TỈNH/THÀNH PHỐ (Xử lý vụ "Thành phố", "TP.", "Tỉnh")
        if(request.getTinhThanhPho() != null && !request.getTinhThanhPho().trim().isEmpty()) {
            // Bước A: Cắt bỏ các tiền tố không cần thiết (Không phân biệt hoa thường)
            // Regex này sẽ dọn sạch "Thành phố ", "TP. ", "TP ", "Tỉnh " ở đầu chuỗi
            String coreCityName = request.getTinhThanhPho().trim()
                    .replaceAll("(?i)^(thành phố|tp\\.?|tỉnh)\\s*", "")
                    .trim()
                    .toLowerCase();
            
            // Bước B: Tìm kiếm tương đối bằng lõi tên đã được làm sạch
            // Ví dụ tìm "%hồ chí minh%" sẽ ra cả "TP. Hồ Chí Minh" lẫn "Thành Phố Hồ Chí Minh"
            builder.and(clinic.tinhThanhPho.lower().like("%" + coreCityName + "%"));
        }
        
        List<ClinicEntity> results = queryFactory
            .selectFrom(clinic)
            .where(builder)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
        
        Long totalCount = queryFactory
            .select(clinic.count())
            .from(clinic)
            .where(builder)
            .fetchOne();
        
        Long total = (totalCount != null) ? totalCount : 0L;
        return new PageImpl<>(results, pageable, total);
    }
}
