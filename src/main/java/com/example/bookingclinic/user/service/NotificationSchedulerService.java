package com.example.bookingclinic.user.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.entity.AccountNotification;
import com.example.bookingclinic.user.entity.Calendar;
import com.example.bookingclinic.user.entity.Notification;
import com.example.bookingclinic.user.entity.NotificationAccountId;
import com.example.bookingclinic.user.repository.AccountNotificationsRepository;
import com.example.bookingclinic.user.repository.CalendarRepository;
import com.example.bookingclinic.user.repository.UNotificationRepository;

@Service
public class NotificationSchedulerService {

    @Autowired
    private AccountNotificationsRepository accountNotificationsRepository;
    @Autowired
    private UNotificationRepository notificationRepository;

    @Autowired
    private CalendarRepository calendarRepository;

    public List<AccountNotification> getAllNotifications(String id) { // lấy toàn bộ thông báo của đối tượng BN
        return accountNotificationsRepository.findByAccount_MaTaiKhoanOrderByNotification_ThoiGianGuiDesc(id);
    }

    public AccountNotification NotificationDetail(String id) { // Lấy 1 thông báo
        return accountNotificationsRepository.findFirstByNotification_MaThongBao(id);
    }

    public void ReadNotification(String idAccount, String idNoti) {
        AccountNotification noti = accountNotificationsRepository
                .findByAccount_MaTaiKhoanAndNotification_MaThongBao(idAccount, idNoti);
        if (noti != null) {
            if (noti.getIsRead() == false) {
                noti.setIsRead(true);
                accountNotificationsRepository.save(noti);
            }
        }
    }

    public void DelNotification(String idAccount, String idNoti) {

        AccountNotification noti = accountNotificationsRepository
                .findByAccount_MaTaiKhoanAndNotification_MaThongBao(idAccount, idNoti);
        if (noti != null) {
            accountNotificationsRepository.delete(noti);
        }
        boolean exists = accountNotificationsRepository.existsByNotification_MaThongBao(idNoti);
        if (!exists) {
            notificationRepository.deleteById(idNoti);
        }
    }

    public int countNotification(String idAccount, Boolean isRead) {
        return accountNotificationsRepository.countByAccount_MaTaiKhoanAndIsRead(idAccount, isRead);
    }

    @Scheduled(fixedRate = 60000)
    public void autoSendNotification() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Calendar> list = calendarRepository.findLichKhamCanThongBao(tomorrow);

        for (Calendar lich : list) {

            try {
                String accountId = lich.getPatient()
                        .getTaiKhoan()
                        .getMaTaiKhoan();

                String notificationId = "TB" + System.currentTimeMillis();

                Notification notification = new Notification();
                notification.setMaThongBao(notificationId);
                notification.setTieuDe("Nhắc nhở: Bạn có lịch khám sắp tới, vui lòng kiểm tra và đến đúng giờ");
                notification.setMaTaiKhoan("SYSTEM");
                notification.setNoiDung(
                        "Bạn có lịch khám vào ngày "
                                + lich.getNgayKham() + " lúc " + lich.getGioKham()
                                + " Hãy kiểm tra lịch khám của mình nhé!");
                notification.setLoaiThongBao("BOOKiNG");
                notification.setDoiTuongNhan(null);
                notification.setThoiGianGui(LocalDateTime.now());
                notification.setMaNguoiNhan(accountId);
                notification.setAnhThongBao(
                        "https://media.istockphoto.com/id/2159733664/vi/anh/loa-m%C3%A0u-xanh-v%E1%BB%9Bi-hoa-gi%E1%BA%A5y-s%C3%A1ng-b%C3%B3ng-ph%C3%A1t-ra-t%E1%BB%AB-n%C3%B3.jpg?s=612x612&w=0&k=20&c=12Q3Soxd0mKASmcolZ54ARkx0PGdS0dH9IJQORx8hBw=");
                notificationRepository.save(notification);

                AccountNotification accNoti = new AccountNotification();

                NotificationAccountId id = new NotificationAccountId(notificationId, accountId);
                accNoti.setId(id);
                accNoti.setNotification(notification);
                accNoti.setAccount(lich.getPatient().getTaiKhoan());
                accNoti.setIsRead(false);

                accountNotificationsRepository.save(accNoti);

                lich.setDaGuiThongBao(1);
                calendarRepository.save(lich);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
