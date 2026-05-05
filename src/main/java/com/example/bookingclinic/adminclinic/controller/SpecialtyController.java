package com.example.bookingclinic.adminclinic.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminclinic.dto.request.SpecialtyRequest;
import com.example.bookingclinic.adminclinic.dto.request.SpecialtySearchRequest;
import com.example.bookingclinic.adminclinic.repository.projection.SpecialtyProjection;
import com.example.bookingclinic.adminclinic.service.SpecialtyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/adminclinic/specialty")
@RequiredArgsConstructor
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    //tất cả danh sách chuyên khoa của phòng khám
    @GetMapping("/all/{maPhongKham}")
    public ResponseEntity<List<SpecialtyProjection>> getAllSpecialties(@PathVariable String maPhongKham) {
        List<SpecialtyProjection> projection = specialtyService.getAllSpecialty(maPhongKham);
        return ResponseEntity.ok(projection);
    }
    //tìm kiếm
    @PostMapping("/search/{maPhongKham}")
    public ResponseEntity<List<SpecialtyProjection>> searchSpecialties(
            @RequestBody SpecialtySearchRequest request,
            @PathVariable String maPhongKham) {
        List<SpecialtyProjection> responses = specialtyService.searchSpecialty(request, maPhongKham);
        return ResponseEntity.ok(responses);
    }
    //Thêm mới chuyên khoa
    @PostMapping("/create/{maPhongKham}")
    public ResponseEntity<SpecialtyProjection> createSpecialty(
            @RequestBody SpecialtyRequest request,
            @PathVariable String maPhongKham) {
        SpecialtyProjection response = specialtyService.createSpecialty(request, maPhongKham);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //Cập nhật chuyên khoa
    @PutMapping("/update/{maPhongKham}/{maChuyenKhoa}")
    public ResponseEntity<SpecialtyProjection> updateSpecialty(
            @PathVariable String maChuyenKhoa,
            @RequestBody SpecialtyRequest request,
            @PathVariable String maPhongKham) {
        SpecialtyProjection response = specialtyService.updateSpecialty(maChuyenKhoa, request, maPhongKham);
        return ResponseEntity.ok(response);
    }
    //Xóa chuyên khoa
    @DeleteMapping("/delete/{maPhongKham}/{maChuyenKhoa}")
    public ResponseEntity<String> deleteSpecialty(
            @PathVariable String maChuyenKhoa,
            @PathVariable String maPhongKham) {
        specialtyService.deleteSpecialty(maChuyenKhoa, maPhongKham);
        return ResponseEntity.ok("Xóa chuyên khoa thành công");
    }
    //Xem chi tiết chuyên khoa
    @GetMapping("/detail/{maPhongKham}/{maChuyenKhoa}")
    public ResponseEntity<SpecialtyProjection> getSpecialtyDetail(
            @PathVariable String maChuyenKhoa,
            @PathVariable String maPhongKham) {
        SpecialtyProjection response = specialtyService.getSpecialtyDetail(maPhongKham, maChuyenKhoa);
        return ResponseEntity.ok(response);
    }
}
