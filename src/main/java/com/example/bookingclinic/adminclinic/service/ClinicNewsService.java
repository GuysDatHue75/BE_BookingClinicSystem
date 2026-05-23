package com.example.bookingclinic.adminclinic.service;

import org.springframework.data.domain.Page;

import com.example.bookingclinic.adminclinic.dto.request.NewsRequest;
import com.example.bookingclinic.adminclinic.dto.request.NewsSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.NewsResponse;
import com.example.bookingclinic.adminclinic.repository.projection.NewsDetailProjection;

public interface ClinicNewsService {
    Page<NewsResponse> search(NewsSearchRequest request, String maPhongKham);

    String createNews(NewsRequest request, String maPhongKham);

    String updateNews(NewsRequest request, String maTinTuc, String maPhongKham);

    void deleteNews(String maTinTuc, String maPhongKham);

    NewsDetailProjection getDetail(String maTinTuc, String maPhongKham);
}
