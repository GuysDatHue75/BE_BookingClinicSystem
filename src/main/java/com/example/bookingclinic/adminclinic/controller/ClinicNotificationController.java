package com.example.bookingclinic.adminclinic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminclinic.dto.request.NotificationRequest;
import com.example.bookingclinic.adminclinic.dto.request.NotificationSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.NotificationResponse;
import com.example.bookingclinic.adminclinic.repository.projection.NotificationProjection;
import com.example.bookingclinic.adminclinic.service.ClinicNotificationService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/v1/adminclinic/notification")
@RequiredArgsConstructor
public class ClinicNotificationController {
    private final ClinicNotificationService notificationService;

    //Tìm kiếm và phân trang
    @PostMapping("/search")
    public ResponseEntity<Page<NotificationResponse>> search(@RequestBody NotificationSearchRequest request) {
        return ResponseEntity.ok(notificationService.search(request));
    }

    //Lấy số lượng thông báo chưa đọc
    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(@RequestParam String maTaiKhoan) {
        return ResponseEntity.ok(notificationService.countUnread(maTaiKhoan));
    }

    //Xem chi tiết thông báo
    @GetMapping("/{maThongBao}")
    public ResponseEntity<NotificationProjection> getDetail(@PathVariable String maThongBao) {
        return ResponseEntity.ok(notificationService.getDetail(maThongBao));
    }
    
    //Đánh dấu đã đọc
    @PutMapping("/{maThongBao}/{maTaiKhoan}/read")
    public ResponseEntity<String> markAsRead(@PathVariable String maThongBao, @PathVariable String maTaiKhoan) {
        notificationService.markAsRead(maThongBao, maTaiKhoan);
        return ResponseEntity.ok("Đã đọc");
    }

    //Tạo thông báo mới
    @PostMapping("/create")
    public ResponseEntity<String> createNotification(@RequestBody NotificationRequest request) {
        notificationService.createNotification(request);
        return ResponseEntity.ok("Tạo thông báo thành công.");
    }
    
    
    
}
