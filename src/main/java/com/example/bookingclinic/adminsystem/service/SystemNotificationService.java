package com.example.bookingclinic.adminsystem.service;

import org.springframework.data.domain.Page;

import com.example.bookingclinic.adminsystem.dto.request.NotificationRequest;
import com.example.bookingclinic.adminsystem.dto.request.NotificationSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.NotificationResponse;
import com.example.bookingclinic.adminsystem.repository.projection.NotificationProjection;

public interface SystemNotificationService {

    Page<NotificationResponse> search(NotificationSearchRequest request);

    void createNotification(NotificationRequest request);

    void updateNotification(NotificationRequest request);

    void deleteNotification(String maThongBao);

    // long countUnread(String maTaiKhoan);

    // void markAsRead(String maThongBao, String maTaiKhoan);

    NotificationProjection getDetail(String maThongBao);

}
