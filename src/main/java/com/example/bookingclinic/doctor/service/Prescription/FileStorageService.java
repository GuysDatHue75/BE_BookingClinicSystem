package com.example.bookingclinic.doctor.service.Prescription;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    // Lấy đường dẫn "uploads" từ file properties bạn đã cấu hình
    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            // Tự động tạo thư mục nếu chưa có
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Không thể tạo thư mục lưu trữ file cục bộ.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Làm sạch tên file gốc
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Kiểm tra bảo mật cơ bản
            if (originalFileName.contains("..")) {
                throw new RuntimeException("Tên file chứa ký tự không hợp lệ: " + originalFileName);
            }

            // Tạo tên file mới bằng UUID để đảm bảo duy nhất 100%
            String fileExtension = "";
            int i = originalFileName.lastIndexOf('.');
            if (i > 0) {
                fileExtension = originalFileName.substring(i);
            }
            String newFileName = UUID.randomUUID().toString() + fileExtension;

            // Copy file vật lý vào thư mục đích
            Path targetLocation = this.fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Trả về tên file mới để lưu vào cột fileUrl trong Database
            // Lưu ý: Chỉ lưu tên file (vd: 123-abc.jpg) thay vì toàn bộ C:/... để dễ dàng
            // chuyển host sau này
            return newFileName;

        } catch (IOException ex) {
            throw new RuntimeException("Lỗi xảy ra trong quá trình ghi dữ liệu file: " + originalFileName, ex);
        }
    }
}