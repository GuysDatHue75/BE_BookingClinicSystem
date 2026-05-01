package com.example.bookingclinic.adminsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminsystem.entity.NotificationAccountEntity;
import com.example.bookingclinic.adminsystem.entity.NotificationAccountId;

@Repository
public interface NotificationAccountRepository extends JpaRepository<NotificationAccountEntity, NotificationAccountId> {
    
    List<NotificationAccountEntity> findByIdMaThongBao(String maThongBao);
    
    long countByIdMaTaiKhoanAndIsReadFalse(String maTaiKhoan);

    @Modifying
    @Query("update NotificationAccountEntity n set n.isRead = true where n.id.maThongBao = :id and n.id.maTaiKhoan = :user")
    void markAsRead(@Param("id") String id, @Param("user") String user);

}
