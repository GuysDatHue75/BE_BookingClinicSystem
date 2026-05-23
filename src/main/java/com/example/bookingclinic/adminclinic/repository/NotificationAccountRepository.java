package com.example.bookingclinic.adminclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminclinic.entity.NotificationAccountEntity;
import com.example.bookingclinic.adminclinic.entity.NotificationAccountId;

@Repository
public interface NotificationAccountRepository extends JpaRepository<NotificationAccountEntity, NotificationAccountId> {

    List<NotificationAccountEntity> findByIdMaThongBao(String maThongBao);
    
    @Query("""
        SELECT COUNT(na) 
        FROM NotificationAccountEntity na 
        WHERE na.account.maTaiKhoan = :maTaiKhoan AND na.isRead = false
    """)
    long countById_MaTaiKhoanAndIsReadFalse(@Param("maTaiKhoan") String maTaiKhoan);

    @Modifying
    @Query("""
        UPDATE NotificationAccountEntity na 
        SET na.isRead = true 
        WHERE na.id.maThongBao = :maThongBao 
            AND na.id.maTaiKhoan = :maTaiKhoan
    """)
    void markAsRead(@Param("maThongBao") String maThongBao, @Param("maTaiKhoan") String maTaiKhoan);

}
