package com.example.bookingclinic.adminsystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminsystem.dto.request.NotificationRequest;
import com.example.bookingclinic.adminsystem.dto.request.NotificationSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.NotificationResponse;
import com.example.bookingclinic.adminsystem.repository.projection.NotificationProjection;
import com.example.bookingclinic.adminsystem.service.SystemNotificationService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;




@RestController
@RequestMapping("/api/v1/adminsystem/notification")
@RequiredArgsConstructor
public class SystemNotificationController {
    private final SystemNotificationService notificationService;
    
    //Tìm kiếm thông báo
    @PostMapping("/search")
    public Page<NotificationResponse> search(@RequestBody NotificationSearchRequest request) {
        return notificationService.search(request);
    }

    //Tạo thông báo mới
    @PostMapping(value = "/create", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createNotification(@ModelAttribute NotificationRequest request) {
        notificationService.createNotification(request);
        return "Tạo thông báo thành công";
    }
    
    // Cập nhật thông báo
    @PutMapping(value = "update/{maThongBao}", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public String update(@PathVariable String maThongBao, @ModelAttribute NotificationRequest request) {
        request.setMaThongBao(maThongBao);
        notificationService.updateNotification(request);
        return "Cập nhật thông báo thành công";
    }

    //Xóa thông báo
    @DeleteMapping("delete/{maThongBao}")
    public String deleteNotification(@PathVariable String maThongBao) {
        notificationService.deleteNotification(maThongBao);
        return "Xóa thông báo thành công";
    }

    //Lấy chi tiết thông báo
    @GetMapping("detail/{maThongBao}")
    public NotificationProjection getDetail(@PathVariable String maThongBao) {
        return notificationService.getDetail(maThongBao);
    }
    
    // đánh dấu đã đọc
    // @PutMapping("{maThongBao}/read")
    // public String markAsRead(@PathVariable String maThongBao, @RequestParam String maTaiKhoan) {
    //     notificationService.markAsRead(maThongBao, maTaiKhoan);
    //     return "Đã đọc";
    // }

    // //đếm số lượng thông báo chưa đọc
    // @GetMapping("/badge")
    // public long getBadge(@RequestParam String maTaiKhoan) {
    //     return notificationService.countUnread(maTaiKhoan);
    // }
    

    
    
}
