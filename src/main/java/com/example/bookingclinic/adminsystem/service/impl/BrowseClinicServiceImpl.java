package com.example.bookingclinic.adminsystem.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicActionRequest;
import com.example.bookingclinic.adminsystem.dto.request.BrowseClinicSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicDetailResponse;
import com.example.bookingclinic.adminsystem.dto.response.BrowseClinicResponse;
import com.example.bookingclinic.adminsystem.entity.AccountEntity;
import com.example.bookingclinic.adminsystem.entity.BrowseClinicEntity;
import com.example.bookingclinic.adminsystem.entity.ClinicEntity;
import com.example.bookingclinic.adminsystem.entity.SubscriptionPackageEntity;
import com.example.bookingclinic.adminsystem.mapper.BrowseClinicMapper;
import com.example.bookingclinic.adminsystem.repository.AccountRepository;
import com.example.bookingclinic.adminsystem.repository.BrowseClinicRepository;
import com.example.bookingclinic.adminsystem.repository.ClinicRepository;
import com.example.bookingclinic.adminsystem.repository.SubscriptionPackageRepository;
import com.example.bookingclinic.adminsystem.service.BrowseClinicService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class BrowseClinicServiceImpl implements BrowseClinicService {

    private static final Logger log = LoggerFactory.getLogger(BrowseClinicServiceImpl.class);
    private final BrowseClinicRepository browseClinicRepository;
    private final ClinicRepository clinicRepository;
    private final BrowseClinicMapper browseClinicMapper;
    private final SubscriptionPackageRepository subscriptionPackageRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Override
    public Page<BrowseClinicResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BrowseClinicEntity> entityPage = browseClinicRepository.findAll(pageable);
        return entityPage.map(browseClinicMapper::toResponseFromBrowseClinic);
    }

    @Override
    public Page<BrowseClinicResponse> search(BrowseClinicSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(),request.getSize());
        Page<BrowseClinicEntity> result = browseClinicRepository.searchBrowseClinic(request, pageable);
        return result.map(entity -> browseClinicMapper.toResponseFromBrowseClinic(entity));
    }

    @Override
    public Page<BrowseClinicResponse> getPending(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BrowseClinicEntity> entityPage = browseClinicRepository.findByTrangThaiOrderByNgayDangKyDesc("Chờ duyệt", pageable);
        return entityPage.map(browseClinicMapper::toResponseFromBrowseClinic);
    }

    private synchronized String generateMaTaiKhoan() {
        return "TK" + System.currentTimeMillis();
    }

    public void approve(BrowseClinicEntity entity) {
        
        SubscriptionPackageEntity goi = subscriptionPackageRepository.findById(entity.getSubpackage().getMaGoi())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy gói với mã: " + entity.getSubpackage().getMaGoi()));
        int thoiGianNgay = goi.getThoiHanNgay();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ngayHetHan = now.plusDays(thoiGianNgay);
        String maTaiKhoan = generateMaTaiKhoan();

        String rawPassword = "123456";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        AccountEntity account = AccountEntity.builder()
                .maTaiKhoan(maTaiKhoan)
                .soDt(entity.getSoDienThoai())
                .matKhau(encodedPassword)
                .vaiTro("PhongKham")
                .hoVaTen(entity.getTenPhongKham())
                .anhDaiDien(entity.getAnhPhongKham())
                .trangThai(true)
                .ngayTao(LocalDateTime.now())
                .ngayCapNhat(LocalDateTime.now())
                .isDeleted(false)
                .build();
        accountRepository.save(account);

        SubscriptionPackageEntity subPackageProxy = SubscriptionPackageEntity.builder().maGoi(entity.getSubpackage().getMaGoi()).build();

        ClinicEntity clinic = ClinicEntity.builder()
                .maPhongKham(entity.getMaPhongKham())
                .tenPhongKham(entity.getTenPhongKham())
                .ngayThanhLap(entity.getNgayThanhLap())
                .ngayDangKy(now)
                .ngayHetHan(ngayHetHan)
                .moTa(entity.getMoTa())
                .soDienThoai(entity.getSoDienThoai())
                .email(entity.getEmail())
                .gioBatDauLamViec(entity.getGioBatDauLamViec())
                .gioKetThucLamViec(entity.getGioKetThucLamViec())
                .giayPhep(entity.getGiayPhep())
                .ngayCap(entity.getNgayCap())
                .noiCap(entity.getNoiCap())
                .nguoiDaiDien(entity.getNguoiDaiDien())
                .soDienThoaiNguoiDaiDien(entity.getSoDienThoaiNguoiDaiDien())
                .loaiHinhPhongKham(entity.getLoaiHinhPhongKham())
                .diaChi(entity.getDiaChi())
                .tinhThanhPho(entity.getTinhThanhPho())
                .soLuongBacSi(entity.getSoLuongBacSi())
                .trangThai("Hoạt động")
                .subpackage(subPackageProxy)
                .isDeleted(false)
                .account(account)
                .anhPhongKham(entity.getAnhPhongKham())
                .build();

        clinicRepository.save(clinic);

        entity.setTrangThai("Đã duyệt");
        browseClinicRepository.save(entity);

        sendAccountEmail(entity.getEmail(), entity.getTenPhongKham(), account.getSoDt(), rawPassword);
    }

    private void sendAccountEmail(String toEmail, String tenPhongKham, String soDt, String rawPassword){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Thông báo: Yêu cầu đăng ký phòng khám đã được duyệt");
            message.setText("Chào" + tenPhongKham + ",\n\n" + 
                "Chúc mừng! Phòng khám của bạn đã được duyệt thành công trên hệ thống.\n\n" +
                    "Dưới đây là thông tin tài khoản quản trị phòng khám của bạn:\n" +
                    "- Tên đăng nhập (Số điện thoại): " + soDt + "\n" +
                    "- Mật khẩu: " + rawPassword + "\n\n" +
                    "Vui lòng đăng nhập vào hệ thống và đổi mật khẩu ngay để đảm bảo an toàn.\n\n" +
                    "Trân trọng,\n" +
                    "Ban Quản Trị Booking Clinic."
            );
            javaMailSender.send(message);
            log.info("Đã gửi email cấp tài khoản thành công tới: {}", toEmail);
        } catch (Exception e) {
            log.error("Lỗi khi gửi email tới {} : {}", toEmail, e.getMessage());
        }    
    }

    public void reject(BrowseClinicEntity entity, String lyDoTuChoi) {
        entity.setTrangThai("Đã từ chối");
        entity.setLyDoTuChoi(lyDoTuChoi);
        browseClinicRepository.save(entity);
    }

    @Override
    @Transactional
    public void handleBrowseClinic(BrowseClinicActionRequest request) {
        BrowseClinicEntity entity = browseClinicRepository.findById(request.getMaPhongKham())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng khám với mã: " + request.getMaPhongKham()));
        if(Boolean.TRUE.equals(request.getIsApproved())){
            approve(entity);
        } else {
            reject(entity, request.getLyDoTuChoi());
        }

    }

    @Override
    public BrowseClinicDetailResponse getDetail(String maPhongKham) {
        BrowseClinicEntity entity = browseClinicRepository.findById(maPhongKham)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng khám với mã: " + maPhongKham));

        return browseClinicMapper.toDetailResponseFromBrowseClinic(entity);
    }
}