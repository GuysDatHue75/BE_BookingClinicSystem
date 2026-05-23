package com.example.bookingclinic.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.entity.AccountNotification;
import com.example.bookingclinic.user.repository.AccountNotificationsRepository;
import com.example.bookingclinic.user.repository.UNotificationRepository;


@Service
public class AccountNotificationService {

    @Autowired
    private AccountNotificationsRepository accountNotificationsRepository;
    @Autowired
    private UNotificationRepository notificationRepository;

    public List<AccountNotification> getAllNotifications(String id) { // lấy toàn bộ thông báo của đối tượng BN
        return accountNotificationsRepository.findByAccount_MaTaiKhoanOrderByNotification_ThoiGianGuiDesc(id);

    }

    public AccountNotification NotificationDetail(String id) { // Lấy 1 thông báo
        return accountNotificationsRepository.findFirstByNotification_MaThongBao(id);
    }

    public void ReadNotification(String idAccount, String idNoti) {
        AccountNotification noti = accountNotificationsRepository.findByAccount_MaTaiKhoanAndNotification_MaThongBao(idAccount, idNoti);
        if (noti != null) {
            if (noti.getIsRead() ==false) {
                noti.setIsRead(true);
                accountNotificationsRepository.save(noti);
            }
        }
    }

    public void DelNotification(String idAccount, String idNoti){
        AccountNotification noti = accountNotificationsRepository.findByAccount_MaTaiKhoanAndNotification_MaThongBao(idAccount, idNoti);
        if (noti != null) {
            accountNotificationsRepository.delete(noti);
        }
        boolean exists = accountNotificationsRepository.existsByNotification_MaThongBao(idNoti);
        if(!exists){
            notificationRepository.deleteById(idNoti);
        }
    }

    public int countNotification(String idAccount, Boolean isRead){
        return accountNotificationsRepository.countByAccount_MaTaiKhoanAndIsRead(idAccount, isRead);
    }
}
