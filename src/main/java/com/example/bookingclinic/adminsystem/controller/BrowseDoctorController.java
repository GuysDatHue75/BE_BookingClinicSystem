package com.example.bookingclinic.adminsystem.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminsystem.dto.request.BrowseDoctorActionRequest;
import com.example.bookingclinic.adminsystem.dto.request.BrowseDoctorSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.BrowseDoctorDetailResponse;
import com.example.bookingclinic.adminsystem.repository.projection.BrowseDoctorProjection;
import com.example.bookingclinic.adminsystem.service.BrowseDoctorService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/v1/adminsystem/browse-doctor")
@RequiredArgsConstructor
public class BrowseDoctorController {

    private final BrowseDoctorService browseDoctorService;

    //lấy tất cả danh sách duyệt bác sĩ
    @GetMapping
    public List<BrowseDoctorProjection> getAll(){
        return browseDoctorService.search(new BrowseDoctorSearchRequest());
    }

    //lấy danh sách bác sĩ chờ duyệt
    @GetMapping("/pending")
    public List<BrowseDoctorProjection> getPending() {
        BrowseDoctorSearchRequest request = new BrowseDoctorSearchRequest();
        request.setTrangThai("Chờ duyệt");
        return browseDoctorService.search(request);
    }

    //Tìm kiếm bác sĩ theo tên bác sĩ, số điện thoại, địa chỉ
    @PostMapping("/search")
    public List<BrowseDoctorProjection> search(@RequestBody BrowseDoctorSearchRequest request) {
        return browseDoctorService.search(request);
    }
    
    //Lọc danh sách duyệt bác sĩ theo trạng thái, chức vụ, học hàm
    @PostMapping("/filter")
    public List<BrowseDoctorProjection> filter(@RequestBody BrowseDoctorSearchRequest request) {
        return browseDoctorService.search(request);
    }

    //Xem chi tiết thông tin môt bác sĩ chờ duyệt
    @GetMapping("/{id}")
    public BrowseDoctorDetailResponse getDetail(@PathVariable("id") String maBacSi) {
        return browseDoctorService.getDetail(maBacSi);
    }
    
    //duyệt(xác nhận) bác sĩ hoặc từ chối bác sĩ
    @PutMapping("/{id}/browse")
    public void handleBrowseDoctor(@PathVariable("id") String maBacSi, @RequestBody BrowseDoctorActionRequest request) {
        request.setMaBacSi(maBacSi);
        browseDoctorService.handleBrowseDoctor(request);
    }
    
}
