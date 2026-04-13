package com.example.bookingclinic.adminsystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminsystem.entity.NotificationEntity;
import com.example.bookingclinic.adminsystem.repository.custom.NotificationRepositoryCustom;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, String>, NotificationRepositoryCustom {
    //search
    List<NotificationEntity> findByTieuDeContainingIgnoreCase(String keyword);

    //filter
    List<NotificationEntity> findByLoaiThongBao(String loaiThongBao);

    List<NotificationEntity> findByDoiTuongNhan(String doiTuongNhan);

    //date
    List<NotificationEntity> findByThoiGianGuiAfter(LocalDateTime thoiGianGui);

    //sort
    List<NotificationEntity> findByLoaiThongBaoOrderByThoiGianGuiDesc(String loaiThongBao);

    

}
