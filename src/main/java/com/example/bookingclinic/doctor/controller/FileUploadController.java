package com.example.bookingclinic.doctor.controller;

import com.example.bookingclinic.doctor.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FileUploadController {

    private final FileService fileService;

    // API dành riêng cho upload ảnh trong lúc chat
    @PostMapping("/upload-chat")
    public ResponseEntity<String> uploadChatFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = fileService.saveFile(file, "chats");
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Lỗi khi lưu file: " + e.getMessage());
        }
    }
}