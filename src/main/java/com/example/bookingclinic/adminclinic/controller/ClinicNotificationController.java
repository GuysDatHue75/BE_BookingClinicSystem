package com.example.bookingclinic.adminclinic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookingclinic.adminclinic.dto.request.NotificationRequest;
import com.example.bookingclinic.adminclinic.dto.request.NotificationSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.NotificationResponse;
import com.example.bookingclinic.adminclinic.repository.projection.NotificationProjection;
import com.example.bookingclinic.adminclinic.service.ClinicNotificationService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/v1/adminclinic/notification")
@RequiredArgsConstructor
public class ClinicNotificationController {
    private final ClinicNotificationService notificationService;

    //Tìm kiếm và phân trang
    @PostMapping("/sent/search")
    public ResponseEntity<Page<NotificationResponse>> searchSentNotifications(
        @RequestBody NotificationSearchRequest request) {

        return ResponseEntity.ok(
        notificationService.searchSentNotifications(request)
        );
    }

    @PostMapping("/received/search")
    public ResponseEntity<Page<NotificationResponse>> searchReceivedNotifications(
        @RequestBody NotificationSearchRequest request) {

        return ResponseEntity.ok(
        notificationService.searchReceivedNotifications(request)
        );
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
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createNotification(@ModelAttribute NotificationRequest request,
            @RequestParam(value = "files", required = false) MultipartFile files,
            @RequestParam(value = "anhThongBao", required = false) MultipartFile anhThongBao
    ) {
        notificationService.createNotification(request, files, anhThongBao);
        return ResponseEntity.ok("Tạo thông báo thành công.");
    }
    
    //Câp nhật thông báo
    @PutMapping(value = "/update/{maThongBao}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateNotification(
            @PathVariable String maThongBao,
            @ModelAttribute NotificationRequest request,
            @RequestParam(value = "files", required = false) MultipartFile files,
            @RequestParam(value = "anhThongBao", required = false) MultipartFile anhThongBao
    ) {
        // Đảm bảo mã thông báo từ URL được set cứng vào request object để xử lý dưới Service chính xác
        request.setMaThongBao(maThongBao);
        notificationService.updateNotification(request, files, anhThongBao);
        return ResponseEntity.ok("Cập nhật thông báo thành công.");
    }

    //Xóa thông báo
    @DeleteMapping("/delete/{maThongBao}")
    public ResponseEntity<String> deleteNotification(@PathVariable String maThongBao) {
        notificationService.deleteNotification(maThongBao);
        return ResponseEntity.ok("Xóa thông báo thành công.");
    }
    
    
}
