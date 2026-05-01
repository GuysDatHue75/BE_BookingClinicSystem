package com.example.bookingclinic.adminsystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<BrowseClinicResponse> getPending() {
        return browseClinicService.getPending();
    }

    //Lấy dánh sách tất cả phòng khám
    @GetMapping("/all")
    public List<BrowseClinicResponse> getAll() {
        return browseClinicService.getAll();
    }

    //Tìm kiểm phòng khám theo tên phòng khám, tên người đại diện, địa chỉ
    @PostMapping("/search")
    public List<BrowseClinicResponse> search(
        @RequestBody BrowseClinicSearchRequest request) {
        return browseClinicService.search(request);
    }

    //Duyệt (Xác thực) phòng khám hoặc từ chối phòng khám
    @PostMapping("/{id}/browse")
    public void handleBrowseClinic(@PathVariable String id, @RequestBody BrowseClinicActionRequest request) {
        browseClinicService.handleBrowseClinic(request);
    }

    @PostMapping("/filter")
    public List<BrowseClinicResponse> filter(
        @RequestBody BrowseClinicSearchRequest request) {
        return browseClinicService.filter(request);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<BrowseClinicDetailResponse> getDetail(@PathVariable("id") String id) {
        return ResponseEntity.ok(browseClinicService.getDetail(id));
    }

}
