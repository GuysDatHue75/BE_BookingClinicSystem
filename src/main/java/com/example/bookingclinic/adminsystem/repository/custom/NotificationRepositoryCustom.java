package com.example.bookingclinic.adminsystem.repository.custom;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.bookingclinic.adminsystem.dto.request.NotificationSearchRequest;
import com.example.bookingclinic.adminsystem.repository.projection.NotificationProjection;

public interface NotificationRepositoryCustom {

    Page<NotificationProjection> search(NotificationSearchRequest request);

    List<NotificationProjection> getAll();

    NotificationProjection getDetail(String maThongBao);
    
}
