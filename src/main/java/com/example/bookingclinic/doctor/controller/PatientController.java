package com.example.bookingclinic.doctor.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.doctor.dto.Patient.PatientRequestDTO;
import com.example.bookingclinic.doctor.dto.Patient.PatientResponseDTO;
import com.example.bookingclinic.doctor.dto.Patient.PatientmanagerDTO;
import com.example.bookingclinic.doctor.entity.Patient;
import com.example.bookingclinic.doctor.service.PatientService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PatientController {
    // Bắt buộc phải có dòng này để khai báo Service (Dùng chữ final)
    private final PatientService patientService;

    // 1. Lấy danh sách bệnh nhân (Có kèm phân trang và tìm kiếm đa năng)
    // http://localhost:8080/api/v1/patient/get-all?page=0&size=4&maBacSi=BS01&maPhongKham=PK01
    @GetMapping("/patient/get-all")
    public ResponseEntity<Page<PatientmanagerDTO>> list(
            @RequestParam String maBacSi,
            @RequestParam String maPhongKham,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(patientService.getDetailedPatients(maBacSi, maPhongKham, keyword, page, size));
    }

    // // 2. thêm bệnh nhân
    // @PostMapping("/patient/add")
    // public ResponseEntity<?> addPatient(@RequestBody PatientRequestDTO
    // patientRequestDTO) {
    // try {
    // Patient patient = patientService.addPatient(patientRequestDTO);
    // return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    // } catch (RuntimeException e) {
    // return ResponseEntity.badRequest().body(e.getMessage());
    // }
    // }

    // 2. Thông tin chi tiết bệnh nhân
    @GetMapping("/patient/get-detail/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientDetail(@PathVariable("id") String maBenhNhan) {
        PatientResponseDTO detail = patientService.getPatientDetail(maBenhNhan);
        return ResponseEntity.ok(detail);
    }

    // 3. cập nhật thông tin bệnh nhân
    // http://localhost:8080/api/v1/patient/update
    @PutMapping("/patient/update")
    public ResponseEntity<?> updatePatient(@RequestBody PatientRequestDTO patientRequestDTO) {
        try {
            // Lúc này Service trả về PatientResponseDTO chứ không phải Entity Patient nữa
            PatientResponseDTO response = patientService.updatePatient(patientRequestDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 4. xóa bệnh nhân
    // http://localhost:8080/api/v1/patient/delete/
    @DeleteMapping("/patient/delete/{maBenhNhan}")
    public ResponseEntity<?> deletePatient(@PathVariable String maBenhNhan) {
        try {
            Patient deletePatient = patientService.deletePatient(maBenhNhan);
            return ResponseEntity.ok("Đã xóa thành công bệnh nhân có mã:" + deletePatient.getMaBenhNhan());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
