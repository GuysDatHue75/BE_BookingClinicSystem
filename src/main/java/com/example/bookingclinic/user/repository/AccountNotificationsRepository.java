package com.example.bookingclinic.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.user.entity.AccountNotification;
import com.example.bookingclinic.user.entity.NotificationAccountId;

@Repository
public interface AccountNotificationsRepository extends JpaRepository<AccountNotification, NotificationAccountId> {
    List<AccountNotification> findByAccount_MaTaiKhoan(String id);
    AccountNotification findFirstByNotification_MaThongBao(String id);
    AccountNotification findByAccount_MaTaiKhoanAndNotification_MaThongBao(String idAccount, String idNoti);
    int countByAccount_MaTaiKhoanAndIsRead(String idAccount, Integer isRead);
}
