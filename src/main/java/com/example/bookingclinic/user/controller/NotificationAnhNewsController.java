package com.example.bookingclinic.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.bookingclinic.user.entity.UNews;
import com.example.bookingclinic.user.repository.UNewsRepository;
import com.example.bookingclinic.user.entity.AccountNotification;
import com.example.bookingclinic.user.service.AccountNotificationService;


@RestController
@RequestMapping("/api/v1")
public class NotificationAnhNewsController {
    @Autowired
    private UNewsRepository uNewsRepository;

    @Autowired
    private AccountNotificationService accountNotificationService;


    public NotificationAnhNewsController(UNewsRepository newsRepository){
        this.uNewsRepository = newsRepository;
    }

    @GetMapping("/clinic/news") // xem toàn bộ tin tức của 1 phòng khám
    public Page<UNews> getListNewsInClinic(@RequestParam String id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "0") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<UNews> result = uNewsRepository.findByPhongKham_MaPhongKham(id,pageable);
        return result;
    }
    @GetMapping("/clinic/t-3news") // xem toàn bộ tin tức của 1 phòng khám được sắp xếp theo thời gian
    public List<UNews> getListNewsTop3InClinic(@RequestParam String id){
        return uNewsRepository.findByPhongKham_MaPhongKhamOrderByNgayTaoDesc(id);
    }
    @GetMapping("/news") // xem toàn bộ tin tức của nhiều phòng khám trong 1 tỉnh
    public Page<UNews> getListNewsInCity(@RequestParam String tp, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "0") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<UNews> result = uNewsRepository.findByPhongKham_TinhThanhPhoContainingIgnoreCase(tp, pageable);
        return result;
    }

    @GetMapping("/news/{id}") // xem chi tiết 1 tin tức
    public UNews getNewsByID(@PathVariable String id){
        return uNewsRepository.findByMaTinTuc(id);
    }

    @GetMapping("/notifications") // lấy toàn bộ thông báo của bệnh nhân
    public List<AccountNotification> getAllNotifications(@RequestParam String id){
        return accountNotificationService.getAllNotifications(id);
    }

    @GetMapping("/notification") // lấy 1 thông báo của bệnh nhân
    public AccountNotification NotificationDetail(@RequestParam String id){
        return accountNotificationService.NotificationDetail(id);
    }

    @PutMapping("/notification") // đọc thông báo
    public void ReadNotification(@RequestParam String idAccount, @RequestParam String idNoti){
        accountNotificationService.ReadNotification(idAccount, idNoti);
    }

    @DeleteMapping("/notification") // xóa thông báo
    public void DelNotification(@RequestParam String idAccount, @RequestParam String idNoti){
        accountNotificationService.DelNotification(idAccount, idNoti);
    }

    @GetMapping("/c-notification") // đếm thông báo
    public int countNotification(@RequestParam String idAccount, @RequestParam Boolean isRead){
        return accountNotificationService.countNotification(idAccount, isRead);
    }

}