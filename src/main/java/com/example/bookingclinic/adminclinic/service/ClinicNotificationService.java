package com.example.bookingclinic.adminclinic.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookingclinic.adminclinic.dto.request.NotificationRequest;
import com.example.bookingclinic.adminclinic.dto.request.NotificationSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.NotificationResponse;
import com.example.bookingclinic.adminclinic.repository.projection.NotificationProjection;

public interface ClinicNotificationService {
    Page<NotificationResponse> searchSentNotifications(NotificationSearchRequest request);

    Page<NotificationResponse> searchReceivedNotifications(NotificationSearchRequest request);

    void createNotification(NotificationRequest request, MultipartFile files, MultipartFile anhThongBao);

    void updateNotification(NotificationRequest request, MultipartFile files, MultipartFile anhThongBao);

    void deleteNotification(String maThongBao);

    long countUnread(String maTaiKhoan);

    void markAsRead(String maThongBao, String maTaiKhoan);

    NotificationProjection getDetail(String maThongBao);
}
