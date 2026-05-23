package com.example.bookingclinic.adminsystem.repository.custom;

import org.springframework.data.domain.Page;

import com.example.bookingclinic.adminsystem.dto.request.NotificationSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.NotificationResponse;
import com.example.bookingclinic.adminsystem.repository.projection.NotificationProjection;

public interface NotificationRepositoryCustom {

    Page<NotificationResponse> search(NotificationSearchRequest request);

    NotificationProjection getDetail(String maThongBao);
    
}
