package com.example.bookingclinic.adminsystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminsystem.dto.request.SubscriptionPackageRequest;
import com.example.bookingclinic.adminsystem.dto.request.SubscriptionPackageSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.SubscriptionPackageResponse;
import com.example.bookingclinic.adminsystem.service.SubscriptionPackageService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/v1/adminsystem/subscriptionpackage")
@RequiredArgsConstructor
public class SubscriptionPackageController {
    private final SubscriptionPackageService service;
    
    //tìm kiếm gói đăng ký
    @PostMapping("/search")
    public Page<SubscriptionPackageResponse> search(@RequestBody SubscriptionPackageSearchRequest request){
        return service.search(request);
    }
    
    
    //Tạo gói đăng ký mới
    @PostMapping("/create")
    public String createPackage(@RequestBody SubscriptionPackageRequest request){
        service.createPackage(request);
        return "Thêm gói đăng ký mới thành công";
    }
    
    //Sửa gói đăng ký
    @PutMapping("/update/{maGoi}")
    public String updatePackage(@PathVariable String maGoi, @RequestBody SubscriptionPackageRequest request){
        request.setMaGoi(maGoi);
        service.updatePackage(request);
        return "Chỉnh sửa gói đăng ký thành công";
    }

    //Xóa gói đăng ký
    @DeleteMapping("delete/{maGoi}")
    public String deletePackage(@PathVariable String maGoi){
        service.deletePackage(maGoi);
        return "Xóa gói đăng ký thành công";
    }
    
}
