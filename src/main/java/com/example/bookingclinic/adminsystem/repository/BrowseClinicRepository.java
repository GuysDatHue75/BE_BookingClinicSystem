package com.example.bookingclinic.adminsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminsystem.entity.BrowseClinicEntity;
import com.example.bookingclinic.adminsystem.repository.custom.BrowseClinicRepositoryCustom;

@Repository
public interface BrowseClinicRepository extends JpaRepository<BrowseClinicEntity, String>, BrowseClinicRepositoryCustom {
    //sort
    List<BrowseClinicEntity> findByTrangThaiOrderByNgayDangKyDesc(String trangThai);

}
