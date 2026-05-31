package com.example.bookingclinic.adminclinic.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import com.example.bookingclinic.adminclinic.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService{
    private final String uploadRootPath = "uploads";

    @Override
    public String uploadFile(MultipartFile file, String folderName) {
        try {
            // Thiết lập đường dẫn thư mục lưu trữ cụ thể (ví dụ: uploads/anhPhongKham)
            Path folderPath = Paths.get(uploadRootPath, folderName);
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            // Sinh tên file ngẫu nhiên để tránh xung đột trùng tên file cũ
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null) {
                throw new RuntimeException("Tên file không hợp lệ");
            }
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + extension;

            // Đường dẫn lưu file vật lý trên máy
            Path filePath = folderPath.resolve(uniqueFileName);
            
            // Thực hiện sao chép luồng dữ liệu file vào thư mục đích
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Trả về đường dẫn URL tương đối để lưu vào cơ sở dữ liệu
            // Frontend có thể gọi đường dẫn này để hiển thị ảnh hoặc tải tài liệu pdf
            return "/uploads/" + folderName + "/" + uniqueFileName;
            
        } catch (IOException e) {
            throw new RuntimeException("Lỗi hệ thống khi lưu trữ tệp tin: " + e.getMessage());
        }
    }
}
