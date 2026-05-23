package com.example.bookingclinic.adminclinic.repository.custom;

import org.springframework.data.domain.Page;

import com.example.bookingclinic.adminclinic.dto.request.NewsSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.NewsResponse;
import com.example.bookingclinic.adminclinic.repository.projection.NewsDetailProjection;

public interface NewsRepositoryCustom {
    
    Page<NewsResponse> search(NewsSearchRequest request, String maPhongKham);

    NewsDetailProjection getDetail(String maTinTuc, String maPhongKham);
}
