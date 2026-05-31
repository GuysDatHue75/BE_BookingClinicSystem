package com.example.bookingclinic.adminclinic.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookingclinic.adminclinic.dto.request.NewsRequest;
import com.example.bookingclinic.adminclinic.dto.request.NewsSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.NewsResponse;
import com.example.bookingclinic.adminclinic.entity.ClinicEntity;
import com.example.bookingclinic.adminclinic.entity.NewsEntity;
import com.example.bookingclinic.adminclinic.repository.NewsRepository;
import com.example.bookingclinic.adminclinic.repository.projection.NewsDetailProjection;
import com.example.bookingclinic.adminclinic.service.ClinicNewsService;
import com.example.bookingclinic.adminclinic.service.FileUploadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClinicNewsServiceImpl implements ClinicNewsService{
    private final NewsRepository newsRepository;
    private final FileUploadService fileUploadService;

    @Override
    public Page<NewsResponse> search(NewsSearchRequest request, String maPhongKham){
        return newsRepository.search(request, maPhongKham);
    }
    
    private synchronized String generateMaTinTuc() {
         return "TT" + System.currentTimeMillis();
    }
    
    @Override
    public String createNews(String maPhongKham, NewsRequest request, MultipartFile anh) {
        String maTinTuc = generateMaTinTuc();
        ClinicEntity clinicProxy = ClinicEntity.builder().maPhongKham(maPhongKham).build();
        String anhUrl = null;
        if (anh != null && !anh.isEmpty()) {
            anhUrl = fileUploadService.uploadFile(anh, "tintuc_anh"); // Lưu vào thư mục uploads/tintuc_anh
        }
        NewsEntity news = NewsEntity.builder()
            .maTinTuc(maTinTuc)
            .tieuDe(request.getTieuDe())
            .moTaNgan(request.getMoTaNgan())
            .noiDung(request.getNoiDung())
            .ngayTao(LocalDateTime.now())
            .ngayCapNhat(LocalDateTime.now())
            .anh(anhUrl)
            .clinic(clinicProxy)
            .isDeleted(false)
            .build();
        newsRepository.save(news);
        return maTinTuc;
    }

    @Override
    public String updateNews(String maTinTuc, String maPhongKham, NewsRequest request, MultipartFile anh) {
        NewsEntity s = newsRepository.findByMaTinTucAndClinic_MaPhongKhamAndIsDeletedFalse(maTinTuc, maPhongKham)
                .orElseThrow(() -> new RuntimeException("Tin tức không tồn tại"));
        s.setTieuDe(request.getTieuDe());
        s.setMoTaNgan(request.getMoTaNgan());
        s.setNoiDung(request.getNoiDung());
        s.setNgayCapNhat(LocalDateTime.now());
        if (anh != null && !anh.isEmpty()) {
            String anhUrl = fileUploadService.uploadFile(anh, "tintuc_anh");
            s.setAnh(anhUrl);
        }
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
