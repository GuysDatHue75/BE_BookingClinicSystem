package com.example.bookingclinic.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.entity.UNews;
import com.example.bookingclinic.user.repository.UNewsRepository;

@RestController
@RequestMapping("api/v1")
public class NotificationAnhNewsController {
    @Autowired
    private UNewsRepository newsRepository;

    public NotificationAnhNewsController(UNewsRepository newsRepository){
        this.newsRepository = newsRepository;
    }

    @GetMapping("/clinic/news") // xem toàn bộ tin tức của 1 phòng khám
    public List<UNews> getListNewsInClinic(@RequestParam String id){
        return newsRepository.findByPhongKham_MaPhongKham(id);
    }

    @GetMapping("/news") // xem toàn bộ tin tức của nhiều phòng khám trong 1 tỉnh
    public List<UNews> getListNewsInCity(@RequestParam String tp){
        return newsRepository.findByPhongKham_TinhThanhPhoContainingIgnoreCase(tp);
    }

    @GetMapping("/news/{id}") // xem chi tiết 1 tin tức
    public UNews getNewsByID(@PathVariable String id){
        return newsRepository.findByMaTinTuc(id);
    }
}
