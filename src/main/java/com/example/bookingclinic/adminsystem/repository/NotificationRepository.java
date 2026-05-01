package com.example.bookingclinic.adminsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminsystem.entity.NotificationEntity;
import com.example.bookingclinic.adminsystem.repository.custom.NotificationRepositoryCustom;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, String>, NotificationRepositoryCustom {

    @Query("SELECT s.maThongBao FROM NotificationEntity s")
    List<String> findAllMaThongBao();
}
