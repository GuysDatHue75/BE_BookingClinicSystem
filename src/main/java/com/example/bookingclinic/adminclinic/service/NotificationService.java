package com.example.bookingclinic.adminclinic.service;

import org.springframework.data.domain.Page;

import com.example.bookingclinic.adminclinic.dto.request.NotificationRequest;
import com.example.bookingclinic.adminclinic.dto.request.NotificationSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.NotificationResponse;
import com.example.bookingclinic.adminclinic.repository.projection.NotificationProjection;

public interface NotificationService {
    Page<NotificationResponse> search(NotificationSearchRequest request);

    void createNotification(NotificationRequest request);

    void updateNotification(NotificationRequest request);

    void deleteNotification(String maThongBao);

    long countUnread(String maTaiKhoan);

    void markAsRead(String maThongBao, String maTaiKhoan);

    NotificationProjection getDetail(String maThongBao);
}
