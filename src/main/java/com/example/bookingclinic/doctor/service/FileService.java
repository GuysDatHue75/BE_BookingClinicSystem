package com.example.bookingclinic.doctor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public String saveFile(MultipartFile file, String subFolder) throws IOException {
        // 1. Tạo đường dẫn đầy đủ: uploads/chats hoặc uploads/avatars
        Path rootPath = Paths.get(uploadDir, subFolder);

        // 2. Nếu thư mục chưa có thì tạo mới
        if (!Files.exists(rootPath)) {
            Files.createDirectories(rootPath);
        }

        // 3. Tạo tên file độc nhất bằng UUID để không bị trùng file cũ
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = rootPath.resolve(fileName);

        // 4. Copy file vào thư mục
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 5. Trả về URL để Frontend có thể truy cập
        // Ví dụ: http://localhost:8080/uploads/chats/ten_file.jpg
        return "http://localhost:8080/" + uploadDir + "/" + subFolder + "/" + fileName;
    }
}