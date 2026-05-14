package com.example.bookingclinic.adminclinic.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminclinic.entity.NotificationEntity;
import com.example.bookingclinic.adminclinic.repository.custom.NotificationRepositoryCustom;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, String>, NotificationRepositoryCustom {

    @Query(value = "SELECT n.maThongBao FROM NotificationEntity n ORDER BY n.maThongBao DESC LIMIT 1")
    String findMaxMaThongBao();
}
