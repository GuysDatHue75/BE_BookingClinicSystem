package com.example.bookingclinic.adminsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminsystem.entity.BrowseClinicEntity;
import com.example.bookingclinic.adminsystem.repository.custom.BrowseClinicRepositoryCustom;

@Repository
public interface BrowseClinicRepository extends JpaRepository<BrowseClinicEntity, String>, BrowseClinicRepositoryCustom {
    //sort
    Page<BrowseClinicEntity> findByTrangThaiOrderByNgayDangKyDesc(String trangThai, Pageable pageable);

}
