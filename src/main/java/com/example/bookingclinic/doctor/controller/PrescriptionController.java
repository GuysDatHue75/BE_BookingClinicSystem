package com.example.bookingclinic.doctor.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookingclinic.doctor.dto.Prescription.MedicalRecordsDTO;
import com.example.bookingclinic.doctor.dto.Prescription.PrescriptionDTO;
import com.example.bookingclinic.doctor.service.Prescription.PrescriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/v1/prescription")
@RequiredArgsConstructor
public class PrescriptionController {
    private final PrescriptionService prescriptionService;
    private final ObjectMapper objectMapper;

    // 1. Tạo đơn thuốc
    // http://localhost:8080/api/v1/prescription/create
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> Create(
            @RequestPart("data") String jsonData,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        try {
            // Ép kiểu chuỗi JSON từ Client gửi lên thành đối tượng MedicalRecordsDTO
            MedicalRecordsDTO dto = objectMapper.readValue(jsonData, MedicalRecordsDTO.class);

            // Đẩy dữ liệu và file xuống Service
            String result = prescriptionService.CreatePrescription(dto, files);
            return ResponseEntity.ok(result);

        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Lỗi logic: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Lỗi định dạng dữ liệu: " + e.getMessage());
        }
    }

    // 2.1 Lấy 1 đơn thuốc
    // http://localhost:8080/api/v1/prescription/get/DT02
    @GetMapping("/get/{maDonThuoc}")
    public ResponseEntity<?> getPrescriptionResponse(@PathVariable String maDonThuoc) {
        try {
            PrescriptionDTO reponseDTO = prescriptionService.getPrescriptionById(maDonThuoc);
            return ResponseEntity.ok(reponseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            // Mã 400 - Lỗi do Client/Người dùng)
            // Ý nghĩa: "Bad Request" nghĩa là Yêu cầu tồi tệ. dùng nó khi lỗi xảy ra DO
            // LỖI CỦA NGƯỜI DÙNG HOẶC FRONTEND truyền sai dữ liệu lên.
        }
    }

    // 2.2 Lấy toàn bộ đơn thuốc
    // http://localhost:8080/api/v1/prescription/get/all
    @GetMapping("/get/all")
    public ResponseEntity<List<PrescriptionDTO>> getAllPrescirptions() {
        try {
            List<PrescriptionDTO> result = prescriptionService.getAllPrescriptions();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
            // Mã 500 - Lỗi do Server/Backend)
            // Ý nghĩa: "Internal Server Error" nghĩa là Lỗi nội bộ máy chủ. dùng nó khi dữ
            // liệu người dùng truyền lên rất chuẩn chỉnh, nhưng HỆ THỐNG BACKEND LẠI BỊ
            // SẬP HOẶC LỖI GÌ ĐÓ BÊN TRONG.
        }
    }

    // 3. Update đơn thuốc
    @PutMapping("update/{maDonThuoc}")
    public ResponseEntity<?> updatePrescription(@PathVariable String maDonThuoc, @RequestBody MedicalRecordsDTO dto) {
        try {
            PrescriptionDTO result = prescriptionService.updatePrescription(maDonThuoc, dto);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Lỗi : " + e.getMessage());
        }
    }

    // 4. XÓA ĐƠN THUỐC
    // http://localhost:8080/api/v1/prescription/delete/DT02
    @DeleteMapping("/delete/{maDonThuoc}")
    public ResponseEntity<String> deletePrescription(@PathVariable String maDonThuoc) {
        try {
            String result = prescriptionService.deletePrescription(maDonThuoc);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body("lỗi : khả năng là bạn đã gửi sai đơn thuốc muốn xóa hahaha" + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi hệ thồng :" + e.getMessage());
        }
    }

    // 5. Lọc đơn thuốc
    // http://localhost:8080/api/v1/prescription/filter?tuNgay=2024-01-01&denNgay=2024-12-31&page=0&size=2
    @GetMapping("/filter")
    public ResponseEntity<?> filterByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tuNgay,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate denNgay,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            if (tuNgay.isAfter(denNgay)) {
                return ResponseEntity.badRequest().body("lỗi : ngày bắt đầu không được phép lớn hơn ngày kết thúc!");
            }

            Page<PrescriptionDTO> result = prescriptionService.filterPrescriptionsByDate(tuNgay, denNgay, page, size);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi hệ thống: " + e.getMessage());
        }
    }

}
