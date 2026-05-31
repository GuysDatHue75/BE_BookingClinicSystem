package com.example.bookingclinic.adminclinic.repository.custom;

import org.springframework.data.domain.Page;

import com.example.bookingclinic.adminclinic.dto.request.NotificationSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.NotificationResponse;
import com.example.bookingclinic.adminclinic.repository.projection.NotificationProjection;

public interface NotificationRepositoryCustom {

    Page<NotificationResponse> searchSentNotifications(NotificationSearchRequest request);

Page<NotificationResponse> searchReceivedNotifications(NotificationSearchRequest request);

    NotificationProjection getDetail(String maThongBao);
    
}
