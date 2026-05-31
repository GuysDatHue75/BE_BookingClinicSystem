package com.example.bookingclinic.adminclinic.service;

import org.springframework.web.multipart.MultipartFile;
public interface FileUploadService {
    String uploadFile(MultipartFile file, String folderName);
    
}
