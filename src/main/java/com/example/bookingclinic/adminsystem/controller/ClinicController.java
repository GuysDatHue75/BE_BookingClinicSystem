package com.example.bookingclinic.adminsystem.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicResponse;
import com.example.bookingclinic.adminsystem.dto.response.ClinicDetailResponse;
import com.example.bookingclinic.adminsystem.service.ClinicService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/adminsystem/clinic")
@RequiredArgsConstructor
public class ClinicController {
    private final ClinicService clinicService;

    //Lấy tất cả phòng khám
    @GetMapping
    public List<BrowseClinicResponse> getAll() {
        return clinicService.getAll();
    }

    //tìm kiếm phòng khám
    @PostMapping("/search")
    public List<BrowseClinicResponse> search(@RequestBody BrowseClinicSearchRequest request) {       
        return clinicService.search(request);
    }

    //Lọc phòng khám
    @PostMapping("/filter")
    public List<BrowseClinicResponse> filter(@RequestBody BrowseClinicSearchRequest request) {
        return clinicService.filter(request);
    }

    //Lấy chi tiết phòng khám
    @GetMapping("/{maPhongKham}")
    public ClinicDetailResponse getDetail(@PathVariable String maPhongKham) {
        return clinicService.getDetail(maPhongKham);
    }

    //Xóa (soft delete) phòng khám
    @DeleteMapping("/{maPhongKham}/delete")
    public String deleteClinic(@PathVariable String maPhongKham) {
        clinicService.deleteClinic(maPhongKham);
        return "Xóa phòng khám thành công";
    }

    //gia hạn gói đăng ký phòng khám
    @PutMapping("/{maPhongKham}/renew")
    public String renewClinic(@PathVariable String maPhongKham) {
        clinicService.renewClinic(maPhongKham);
        return "Gia hạn phòng khám thành công";
    }
    
}
