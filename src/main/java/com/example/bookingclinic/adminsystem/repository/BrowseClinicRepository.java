package com.example.bookingclinic.adminsystem.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminsystem.entity.BrowseClinicEntity;
import com.example.bookingclinic.adminsystem.repository.custom.BrowseClinicRepositoryCustom;

@Repository
public interface BrowseClinicRepository extends JpaRepository<BrowseClinicEntity, String>, BrowseClinicRepositoryCustom {
    //Basic
    Optional<BrowseClinicEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsBySoDienThoai(String soDienThoai);

    //search
    List<BrowseClinicEntity> findByTenPhongKhamContainingIgnoreCase(String keyword);

    List<BrowseClinicEntity> findByTinhThanhPhoContainingIgnoreCase(String tinhThanhPho);

    List<BrowseClinicEntity> findByNguoiDaiDienContainingIgnoreCase(String nguoiDaiDien);

    List<BrowseClinicEntity> findByDiaChiContainingIgnoreCase(String diaChi);

    //filter
    List<BrowseClinicEntity> findByTrangThai(String trangThai);

    //date
    List<BrowseClinicEntity> findByNgayThanhLapAfter(LocalDateTime ngayThanhLap);

    List<BrowseClinicEntity> findByNgayCapBefore(LocalDateTime ngayCap);

    List<BrowseClinicEntity> findByNgayDangKyAfter(LocalDateTime ngayDangKy);

    //sort
    List<BrowseClinicEntity> findByTrangThaiOrderByNgayDangKyDesc(String trangThai);

    //null
    List<BrowseClinicEntity> findByTepDinhKemIsNotNull();

}
