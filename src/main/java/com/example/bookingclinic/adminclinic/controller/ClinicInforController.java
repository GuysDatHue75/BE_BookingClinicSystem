package com.example.bookingclinic.adminclinic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminclinic.dto.request.ClinicRequest;
import com.example.bookingclinic.adminclinic.dto.response.ClinicResponse;
import com.example.bookingclinic.adminclinic.service.ClinicInforService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/v1/adminclinic/clinic")
@RequiredArgsConstructor
public class ClinicInforController {

    private final ClinicInforService clinicService;
    
    //lấy thông tin chi tiết phòng khám
    @GetMapping("/detail/{maPhongKham}")
    public ClinicResponse getDetail(@PathVariable String maPhongKham) {
        return clinicService.getDetail(maPhongKham);
    }

    //Chỉnh sửa(cập nhật) thông tin phòng khám
    @PutMapping("/update/{maPhongKham}")
    public String updateClinic(@PathVariable String maPhongKham, @RequestBody ClinicRequest request) {
        clinicService.updateClinic(maPhongKham, request);
        return "Cập nhật thông tin phòng khám thành công";
    }
    
}
