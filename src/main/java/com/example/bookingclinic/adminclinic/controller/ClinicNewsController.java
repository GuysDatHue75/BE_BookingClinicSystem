package com.example.bookingclinic.adminclinic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminclinic.dto.request.NewsRequest;
import com.example.bookingclinic.adminclinic.dto.request.NewsSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.NewsResponse;
import com.example.bookingclinic.adminclinic.repository.projection.NewsDetailProjection;
import com.example.bookingclinic.adminclinic.service.ClinicNewsService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/v1/adminclinic/news")
@RequiredArgsConstructor
public class ClinicNewsController {
    private final ClinicNewsService newsService;
    
    //tìm kiếm tin tức
    @PostMapping("/search/{maPhongKham}")
    public ResponseEntity<Page<NewsResponse>> searchNews (@RequestBody NewsSearchRequest request, @PathVariable String maPhongKham) {
        return ResponseEntity.ok(newsService.search(request, maPhongKham));
    }
    
    //thêm tn tức
    @PostMapping("/create/{maPhongKham}")
    public ResponseEntity<String> createNews (@RequestBody NewsRequest request, @PathVariable String maPhongKham) {
        return ResponseEntity.ok(newsService.createNews(request, maPhongKham));
    }
    
    //sửa tin tức
    @PutMapping("/update/{maTinTuc}/{maPhongKham}")
    public ResponseEntity<String> updateNews (@RequestBody NewsRequest request, @PathVariable String maTinTuc, @PathVariable String maPhongKham){
        return ResponseEntity.ok(newsService.updateNews(request, maTinTuc, maPhongKham));
    }
    //xóa tin tức
    @DeleteMapping("/{maTinTuc}/{maPhongKham}")
    public ResponseEntity<String> deleteNews(@PathVariable String maTinTuc, @PathVariable String maPhongKham){
        newsService.deleteNews(maTinTuc, maPhongKham);
        return ResponseEntity.ok("Xóa tin tức thành công");
    }
    //xem chi tiết tin tức
    @GetMapping("/{maPhongKham}/{maTinTuc}")
    public ResponseEntity<NewsDetailProjection> getDetail(@PathVariable String maPhongKham, @PathVariable String maTinTuc) {
        return ResponseEntity.ok(newsService.getDetail(maTinTuc, maPhongKham));
    }
    
}
