package com.example.bookingclinic.adminclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminclinic.entity.SpecialtyEntity;
import com.example.bookingclinic.adminclinic.repository.custom.SpecialtyRepositoryCustom;
import com.example.bookingclinic.adminclinic.repository.projection.SpecialtyProjection;

@Repository
public interface SpecialtyRepository extends JpaRepository<SpecialtyEntity, String>, SpecialtyRepositoryCustom {
    @Query("SELECT s.maChuyenKhoa FROM SpecialtyEntity s")
    List<String> findAllMaChuyenKhoa();

    Optional<SpecialtyEntity> findByMaChuyenKhoaAndMaPhongKhamAndIsDeletedFalse(String maPhongKham, String maChuyenKhoa);

    List<SpecialtyProjection> findAllSpecialtyByClinicId(String maPhongKham);

}
