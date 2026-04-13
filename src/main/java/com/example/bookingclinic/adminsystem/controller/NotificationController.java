package com.example.bookingclinic.adminsystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminsystem.dto.request.NotificationRequest;
import com.example.bookingclinic.adminsystem.dto.request.NotificationSearchRequest;
import com.example.bookingclinic.adminsystem.repository.projection.NotificationProjection;
import com.example.bookingclinic.adminsystem.service.NotificationService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api/v1/adminsystem/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    //Tìm kiếm thông báo
    @PostMapping("/search")
    public Page<NotificationProjection> search(@RequestBody NotificationSearchRequest request) {
        return notificationService.search(request);
    }

    //Lọc thông báo
     @PostMapping("/filter")
     public Page<NotificationProjection> filter(@RequestBody NotificationSearchRequest request) {
         return notificationService.search(request);
    }

    //Tạo thông báo mới
    @PostMapping("/create")
    public String createNotification(@RequestBody NotificationRequest request) {
        notificationService.createNotification(request);
        return "Tạo thông báo thành công";
    }
    
    //Cập nhật thông báo
    @PutMapping("update/{id}")
    public String update(@RequestBody NotificationRequest request) {
        notificationService.updateNotification(request);
        return "Cập nhật thông báo thành công";
    }

    //Xóa thông báo
    @DeleteMapping("/{maThongBao}")
    public String deleteNotification(@PathVariable String maThongBao) {
        notificationService.deleteNotification(maThongBao);
        return "Xóa thông báo thành công";
    }

    //Lấy chi tiết thông báo
    @GetMapping("/{maThongBao}")
    public NotificationProjection getDetail(@PathVariable String maThongBao, @RequestParam String maTaiKhoan) {
        return notificationService.getDetail(maThongBao, maTaiKhoan);
    }
    
    // đánh dấu đã đọc
    @PutMapping("{maThongBao}/read")
    public String markAsRead(@PathVariable String maThongBao, @RequestParam String maTaiKhoan) {
        notificationService.markAsRead(maThongBao, maTaiKhoan);
        return "Đã đọc";
    }

    //đếm số lượng thông báo chưa đọc
    @GetMapping("/badge")
    public long getBadge(@RequestParam String maTaiKhoan) {
        return notificationService.countUnread(maTaiKhoan);
    }
    

    
    
}
