package com.example.bookingclinic.adminsystem.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicActionRequest;
import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicDetailResponse;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicResponse;
import com.example.bookingclinic.adminsystem.service.BrowseClinicService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/adminsystem/browse-clinic")
@RequiredArgsConstructor
public class BrowseClinicController {
    private final BrowseClinicService browseClinicService;

    //Lấy danh sách phòng khám chờ duyệt
    @GetMapping("/pending")
    public Page<BrowseClinicResponse> getPending(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int size) {
        return browseClinicService.getPending(page, size);
    }

    //Lấy dánh sách tất cả phòng khám
    @GetMapping("/all")
    public Page<BrowseClinicResponse> getAll(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int size) {
        return browseClinicService.getAll(page, size);
    }

    //Tìm kiểm phòng khám theo tên phòng khám, tên người đại diện, địa chỉ
    @PostMapping("/search")
    public Page<BrowseClinicResponse> search(
        @RequestBody BrowseClinicSearchRequest request) {
        return browseClinicService.search(request);
    }

    //Duyệt (Xác thực) phòng khám hoặc từ chối phòng khám
    @PostMapping("/{maPhongKham}/browse")
    public void handleBrowseClinic(@PathVariable String maPhongKham, @RequestBody BrowseClinicActionRequest request) {
        browseClinicService.handleBrowseClinic(request);
    }

    @GetMapping("/detail/{maPhongKham}")
    public ResponseEntity<BrowseClinicDetailResponse> getDetail(@PathVariable String maPhongKham) {
        return ResponseEntity.ok(browseClinicService.getDetail(maPhongKham));
    }

}
