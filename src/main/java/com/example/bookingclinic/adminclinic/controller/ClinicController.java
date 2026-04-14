package com.example.bookingclinic.adminclinic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminclinic.dto.request.ClinicRequest;
import com.example.bookingclinic.adminclinic.dto.response.ClinicResponse;
import com.example.bookingclinic.adminclinic.service.ClinicService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/v1/adminclinic/clinic")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;
    
    //lấy thông tin chi tiết phòng khám
    @GetMapping("/{maPhongKham}")
    public ClinicResponse getDetail(@PathVariable String maPhongKham) {
        return clinicService.getDetail(maPhongKham);
    }

    //Chỉnh sửa(cập nhật) thông tin phòng khám
    @PutMapping("update/{id}")
    public String updateClinic(@RequestBody ClinicRequest request) {
        clinicService.updateClinic(request);
        return "Cập nhật thông tin phòng khám thành công";
    }
    
}
