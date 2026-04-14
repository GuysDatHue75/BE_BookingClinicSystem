package com.example.bookingclinic.adminclinic.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminclinic.entity.NotificationAccountEntity;
import com.example.bookingclinic.adminclinic.entity.NotificationAccountId;

@Repository
public interface NotificationAccountRepository extends JpaRepository<NotificationAccountEntity, NotificationAccountId> {

    long countByIdMaTaiKhoanAndReadFalse(String maTaiKhoan);

    @Modifying
    @Query("update NotificationAccountEntity n set n.isRead = true where n.id.maThongBao = :id and n.id.maTaiKhoan = :user")
    void markAsRead(@Param("id") String id, @Param("user") String user);

}
