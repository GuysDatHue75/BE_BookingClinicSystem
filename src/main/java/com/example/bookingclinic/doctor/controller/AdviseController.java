package com.example.bookingclinic.doctor.controller;

import com.example.bookingclinic.doctor.entity.Advise;
import com.example.bookingclinic.doctor.service.AdviseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/doctor/advise")
@RequiredArgsConstructor
public class AdviseController {

    private final AdviseService adviseService;

    // GET /api/v1/doctor/advise/pending

    @GetMapping("/pending")
    public ResponseEntity<List<Advise>> getPending() {
        List<Advise> list = adviseService.getListPending();
        return ResponseEntity.ok(list);
    }

    // POST /api/v1/doctor/advise/reply/{maTuVan}?maBacSi=BS01
    @PostMapping("/reply/{maTuVan}")
    public ResponseEntity<?> reply(
            @PathVariable String maTuVan,
            @RequestParam String maBacSi,
            @RequestBody Map<String, String> request) {
        try {
            String noiDung = request.get("cauTraLoi");
            Advise result = adviseService.replyAdvise(maTuVan, maBacSi, noiDung);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            // Trả về lỗi 400 kèm thông báo nếu có tranh chấp (bác sĩ khác đã trả lời)
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}