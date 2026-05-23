package com.example.bookingclinic.adminclinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminclinic.entity.NewsEntity;
import com.example.bookingclinic.adminclinic.repository.custom.NewsRepositoryCustom;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, String>, NewsRepositoryCustom{
    Optional<NewsEntity> findByMaTinTucAndClinic_MaPhongKhamAndIsDeletedFalse(String maTinTuc, String maPhongKham);
}
