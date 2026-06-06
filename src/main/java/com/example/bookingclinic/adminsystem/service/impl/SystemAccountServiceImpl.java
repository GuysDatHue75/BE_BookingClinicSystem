package com.example.bookingclinic.adminsystem.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookingclinic.adminclinic.entity.AccountEntity;
import com.example.bookingclinic.adminsystem.dto.request.AccountRequest;
import com.example.bookingclinic.adminsystem.dto.request.AccountSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.AccountResponse;
import com.example.bookingclinic.adminsystem.dto.response.PageResponse;
import com.example.bookingclinic.adminsystem.repository.SystemAccountRepository;
import com.example.bookingclinic.adminsystem.repository.projection.AccountProjection;
import com.example.bookingclinic.adminsystem.service.SystemAccountService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class SystemAccountServiceImpl implements SystemAccountService {
    private static final Logger log = LoggerFactory.getLogger(BrowseClinicServiceImpl.class);
    private final SystemAccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
 
    private synchronized String generateMaTaiKhoan() {
        return "TK" + System.currentTimeMillis();
    }
 
    @Override
    @Transactional(readOnly = true)
    public PageResponse<AccountResponse> searchAccounts(AccountSearchRequest request) {
        return accountRepository.search(request);
    }
 
    @Override
    @Transactional(readOnly = true)
    public AccountProjection getAccountDetail(String maTaiKhoan) {
        AccountProjection projection = accountRepository.getDetail(maTaiKhoan);
        if (projection == null) {
            throw new RuntimeException("Tài khoản không tồn tại: " + maTaiKhoan);
        }
        return projection;
    }

    private String saveAvatarImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            String uploadDir = "uploads/avatars/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs(); // Tự động tạo thư mục nếu chưa tồn tại
            }

            // Tạo tên file ngẫu nhiên để không bị trùng (vd: 16843..._hinhanh.png)
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + filename);
            
            // Lưu file vào ổ cứng
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            
            // Trả về đường dẫn để lưu vào DB (vd: /uploads/avatars/hinhanh.png)
            return "/" + uploadDir + filename; 
        } catch (Exception e) {
            log.error("Lỗi khi lưu ảnh đại diện: {}", e.getMessage());
            return null; // Nếu lưu lỗi thì bỏ qua, không làm sập tiến trình
        }
    }
 
    @Override
    @Transactional
    public String createAccount(AccountRequest request) {
        if (accountRepository.existsBySoDt(request.getSoDt())) {
            throw new RuntimeException("Số điện thoại đã tồn tại");
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()
                && accountRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }
 
        String rawPassword = (request.getMatKhau() != null && !request.getMatKhau().isBlank())
                ? request.getMatKhau() : "123456";

        String avatarPath = saveAvatarImage(request.getAnhDaiDien());
 
        AccountEntity account = AccountEntity.builder()
                .maTaiKhoan(generateMaTaiKhoan())
                .soDt(request.getSoDt())
                .matKhau(passwordEncoder.encode(rawPassword))
                .vaiTro(request.getVaiTro())
                .hoVaTen(request.getHoVaTen())
                .email(request.getEmail())
                .anhDaiDien(avatarPath)
                .trangThai(request.getTrangThai() != null ? request.getTrangThai() : true)
                .ngayTao(LocalDateTime.now())
                .ngayCapNhat(LocalDateTime.now())
                .isDeleted(false)
                .build();
 
        accountRepository.save(account);
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            sendAccountEmail(
                request.getEmail(), 
                request.getHoVaTen(), 
                request.getVaiTro(), 
                request.getSoDt(), 
                rawPassword
            );
        }
        return account.getMaTaiKhoan();
    }
 
    @Override
    @Transactional
    public void updateAccount(String maTaiKhoan, AccountRequest request) {
        AccountEntity account = accountRepository.findById(maTaiKhoan)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));
 
        // if (!account.getSoDt().equals(request.getSoDt())
        //         && accountRepository.existsBySoDtAndMaTaiKhoanNot(request.getSoDt(), maTaiKhoan)) {
        //     throw new RuntimeException("Số điện thoại đã được sử dụng bởi tài khoản khác");
        // }
        // if (request.getEmail() != null && !request.getEmail().isBlank()
        //         && accountRepository.existsByEmailAndMaTaiKhoanNot(request.getEmail(), maTaiKhoan)) {
        //     throw new RuntimeException("Email đã được sử dụng bởi tài khoản khác");
        // }
 
        account.setSoDt(request.getSoDt());
        account.setVaiTro(request.getVaiTro());
        account.setHoVaTen(request.getHoVaTen());
        account.setEmail(request.getEmail());
        account.setNgayCapNhat(LocalDateTime.now());
 
        if (request.getTrangThai() != null) {
            account.setTrangThai(request.getTrangThai());
        }

        if (request.getAnhDaiDien() != null && !request.getAnhDaiDien().isEmpty()) {
            String newAvatarPath = saveAvatarImage(request.getAnhDaiDien());
            account.setAnhDaiDien(newAvatarPath);
        }

        boolean isPasswordChanged = false;
        String rawNewPassword = request.getMatKhau();

        if (rawNewPassword != null && !rawNewPassword.isBlank()) {
            account.setMatKhau(passwordEncoder.encode(rawNewPassword));
            isPasswordChanged = true;
        }
 
        accountRepository.save(account);

        if (isPasswordChanged && account.getEmail() != null && !account.getEmail().isBlank()) {
            sendPasswordUpdateEmail(account.getEmail(), account.getHoVaTen(), rawNewPassword);
        }
    }
 
    private void sendAccountEmail(String toEmail, String hoVaTen, String vaiTro, String soDt, String rawPassword){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Thông báo: Cấp tài khoản hệ thống Booking Clinic");
            
            // Xây dựng nội dung động dựa vào tham số truyền vào
            String text = "Chào " + hoVaTen + ",\n\n" + 
                "Tài khoản của bạn trên hệ thống Booking Clinic đã được tạo thành công với vai trò: " + vaiTro + ".\n\n" +
                "Dưới đây là thông tin đăng nhập hệ thống của bạn:\n" +
                "- Tên đăng nhập (Số điện thoại): " + soDt + "\n" +
                "- Mật khẩu: " + rawPassword + "\n\n" +
                "Vui lòng đăng nhập vào hệ thống và tiến hành đổi mật khẩu ngay để đảm bảo an toàn thông tin.\n\n" +
                "Trân trọng,\n" +
                "Ban Quản Trị Booking Clinic.";
                
            message.setText(text);
            javaMailSender.send(message);
            log.info("Đã gửi email cấp tài khoản thành công tới: {}", toEmail);
        } catch (Exception e) {
            log.error("Lỗi khi gửi email tới {} : {}", toEmail, e.getMessage());
        }    
    }

    private void sendPasswordUpdateEmail(String toEmail, String hoVaTen, String rawNewPassword) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Cảnh báo bảo mật: Mật khẩu tài khoản Booking Clinic đã thay đổi");
            
            String text = "Chào " + hoVaTen + ",\n\n" + 
                "Hệ thống ghi nhận mật khẩu của tài khoản Booking Clinic liên kết với email này vừa được thay đổi thành công.\n\n" +
                "Thông tin mật khẩu mới của bạn:\n" +
                "- Mật khẩu: " + rawNewPassword + "\n\n" +
                "Nếu bạn KHÔNG thực hiện hành động thay đổi này, tài khoản của bạn có thể đang gặp nguy hiểm. Vui lòng liên hệ ngay lập tức với Ban Quản Trị hoặc sử dụng tính năng quên mật khẩu để khôi phục.\n\n" +
                "Trân trọng,\n" +
                "Ban Quản Trị Booking Clinic.";
                
            message.setText(text);
            javaMailSender.send(message);
            log.info("Đã gửi email thông báo đổi mật khẩu thành công tới: {}", toEmail);
        } catch (Exception e) {
            log.error("Lỗi khi gửi email thông báo đổi mật khẩu tới {} : {}", toEmail, e.getMessage());
        }    
    }

    @Override
    @Transactional
    public void deleteAccount(String maTaiKhoan) {
        AccountEntity account = accountRepository.findById(maTaiKhoan)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));
 
        account.setIsDeleted(true);
        account.setTrangThai(false);
        account.setNgayCapNhat(LocalDateTime.now());
        accountRepository.save(account);
    }
}
