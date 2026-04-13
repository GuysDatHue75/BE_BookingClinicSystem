package com.example.bookingclinic.adminsystem.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminsystem.entity.ClinicEntity;
import com.example.bookingclinic.adminsystem.repository.custom.BrowseClinicRepositoryCustom;

@Repository
public interface ClinicRepository extends JpaRepository<ClinicEntity, String>, BrowseClinicRepositoryCustom {
    //basic
    Optional<ClinicEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsBySoDienThoai(String soDienThoai);

    //search
    List<ClinicEntity> findByTenPhongKhamContainingIgnoreCase(String keyword);

    List<ClinicEntity> findByDiaChiContainingIgnoreCase(String diaChi);

    List<ClinicEntity> findByNguoiDaiDienContainingIgnoreCase(String nguoiDaiDien);

    //filter
    List<ClinicEntity> findByTrangThai(String trangThai);

    List<ClinicEntity> findByLoaiHinhPhongKham(String loaiHinhPhongKham);

    List<ClinicEntity> findByMaGoi(String maGoi);

    List<ClinicEntity> findByTinhThanhPhoContainingIgnoreCase(String tinhThanhPho);

    //date
    List<ClinicEntity> findByNgayThanhLapAfter(LocalDateTime ngayThanhLap);

    List<ClinicEntity> findByNgayCapBefore(LocalDateTime ngayCap);

    List<ClinicEntity> findByNgayDangKyAfter(LocalDateTime ngayDangKy);

    //sort
    List<ClinicEntity> findByTrangThaiOrderByNgayDangKyDesc(String trangThai);

    //null
    List<ClinicEntity> findByTepDinhKemIsNotNull();

    List<ClinicEntity> findByIsDeletedFalse();

    List<ClinicEntity> findByNgayHetHanBeforeAndIsDeletedFalse(LocalDateTime time);

}
