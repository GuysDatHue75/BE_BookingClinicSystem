package com.example.bookingclinic.adminclinic.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookingclinic.adminclinic.dto.request.NewsRequest;
import com.example.bookingclinic.adminclinic.dto.request.NewsSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.NewsResponse;
import com.example.bookingclinic.adminclinic.entity.ClinicEntity;
import com.example.bookingclinic.adminclinic.entity.NewsEntity;
import com.example.bookingclinic.adminclinic.repository.NewsRepository;
import com.example.bookingclinic.adminclinic.repository.projection.NewsDetailProjection;
import com.example.bookingclinic.adminclinic.service.NewsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsServiceImpl implements NewsService{
    private final NewsRepository newsRepository;

    @Override
    public Page<NewsResponse> search(NewsSearchRequest request, String maPhongKham){
        return newsRepository.search(request, maPhongKham);
    }
    
    private synchronized String generateMaTinTuc() {
         return "TT" + System.currentTimeMillis();
    }
    
    @Override
    public String createNews(NewsRequest request, String maPhongKham) {
        String maTinTuc = generateMaTinTuc();
        ClinicEntity clinicProxy = ClinicEntity.builder().maPhongKham(maPhongKham).build();
        NewsEntity news = NewsEntity.builder()
            .maTinTuc(maTinTuc)
            .tieuDe(request.getTieuDe())
            .moTaNgan(request.getMoTaNgan())
            .noiDung(request.getNoiDung())
            .ngayTao(LocalDateTime.now())
            .ngayCapNhat(LocalDateTime.now())
            .anh(request.getAnh())
            .clinic(clinicProxy)
            .isDeleted(false)
            .build();
        newsRepository.save(news);
        return maTinTuc;
    }

    @Override
    public String updateNews(NewsRequest request, String maTinTuc, String maPhongKham) {
        NewsEntity s = newsRepository.findByMaTinTucAndClinic_MaPhongKhamAndIsDeletedFalse(maTinTuc, maPhongKham)
                .orElseThrow(() -> new RuntimeException("Tin tức không tồn tại"));
        s.setTieuDe(request.getTieuDe());
        s.setMoTaNgan(request.getMoTaNgan());
        s.setNoiDung(request.getNoiDung());
        s.setNgayCapNhat(LocalDateTime.now());
        s.setAnh(request.getAnh());
        newsRepository.save(s);
        return maTinTuc;
    }

    @Override
    public void deleteNews(String maTinTuc, String maPhongKham) {
        NewsEntity s = newsRepository.findByMaTinTucAndClinic_MaPhongKhamAndIsDeletedFalse(maTinTuc, maPhongKham)
                .orElseThrow(() -> new RuntimeException("Tin tức không tồn tại"));
        s.setIsDeleted(true);
        newsRepository.save(s);
    }

    @Override
    public NewsDetailProjection getDetail(String maTinTuc, String maPhongKham){
        return newsRepository.getDetail(maTinTuc, maPhongKham);
    }
}
