package com.example.bookingclinic.adminclinic.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookingclinic.adminclinic.dto.request.DoctorRequest;
import com.example.bookingclinic.adminclinic.dto.request.DoctorSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.DoctorResponse;
import com.example.bookingclinic.adminclinic.entity.AccountEntity;
import com.example.bookingclinic.adminclinic.entity.ClinicEntity;
import com.example.bookingclinic.adminclinic.entity.DoctorEntity;
import com.example.bookingclinic.adminclinic.entity.SpecialtyEntity;
import com.example.bookingclinic.adminclinic.repository.AccountRepository;
import com.example.bookingclinic.adminclinic.repository.DoctorRepository;
import com.example.bookingclinic.adminclinic.repository.projection.DoctorProjection;
import com.example.bookingclinic.adminclinic.service.DoctorService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private static final Logger log = LoggerFactory.getLogger(DoctorServiceImpl.class);

    private final DoctorRepository doctorRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    private synchronized String generateMaTaiKhoan() {
         return "TK" + System.currentTimeMillis();
    }
    private String generateMaBacSi() {
        Integer maxNumber = doctorRepository.findMaxDoctorIdNumber();
        int nextNumber = (maxNumber == null ? 0 : maxNumber) + 1;
        return String.format("BS%02d", nextNumber);
    }

    @Override
    @Transactional
    public String createDoctor(DoctorRequest request, String maPhongKham) {
        if(accountRepository.existsBySoDt(request.getSoDienThoai())) {
            throw new RuntimeException("Số điện thoại đã tồn tại");
        }

        String maTaiKhoan = generateMaTaiKhoan();
        String maBacSi = generateMaBacSi();
        String rawPassword = "123456";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        AccountEntity account = AccountEntity.builder()
                .maTaiKhoan(maTaiKhoan)
                .soDt(request.getSoDienThoai())
                .matKhau(encodedPassword)
                .vaiTro("BacSi")
                .hoVaTen(request.getTenBacSi())
                .anhDaiDien(request.getAvt())
                .trangThai(true)
                .ngayTao(LocalDateTime.now())
                .ngayCapNhat(LocalDateTime.now())
                .isDeleted(false)
                .build();
        accountRepository.save(account);

        SpecialtyEntity specialtyProxy = SpecialtyEntity.builder().maChuyenKhoa(request.getMaChuyenKhoa()).build();
        ClinicEntity clinicProxy = ClinicEntity.builder().maPhongKham(maPhongKham).build();

        DoctorEntity doctor = DoctorEntity.builder()
                .maBacSi(maBacSi)
                .tenBacSi(request.getTenBacSi())
                .gioiTinh(request.isGioiTinh())
                .soDienThoai(request.getSoDienThoai())
                .email(request.getEmail())
                .diaChi(request.getDiaChi())
                .avt(request.getAvt())
                .specialty(specialtyProxy)
                .bangCap(request.getBangCap())
                .kinhNghiem(request.getKinhNghiem())
                .hoatDong(request.getHoatDong())
                .mieuTa(request.getMieuTa())
                .chucVu(request.getChucVu())
                .hocHam(request.getHocHam())
                .cccd(request.getCccd())
                .soGiayPhep(request.getSoGiayPhep())
                .ngayCap(request.getNgayCap())
                .noiCap(request.getNoiCap())
                .account(account)
                .clinic(clinicProxy)
                .ngayDangKy(LocalDateTime.now())
                .tepDinhKem(request.getTepDinhKem())
                .isDeleted(false)
                .build();
        doctorRepository.save(doctor);

        sendAccountEmail(request.getEmail(), request.getTenBacSi(), account.getSoDt(), rawPassword);

        return maBacSi;
    }

    private void sendAccountEmail(String toEmail, String tenBacSi, String soDt, String rawPassword){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Thông báo: Yêu cầu đăng ký phòng khám đã được duyệt");
            message.setText("Chào" + tenBacSi + ",\n\n" + 
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

    @Override
    @Transactional
    public void deleteDoctor(String maBacSi) {
        DoctorEntity doctor = doctorRepository.findById(maBacSi)
                .orElseThrow(() -> new RuntimeException("Bác sĩ không tồn tại"));

        doctor.setIsDeleted(true);
        doctorRepository.save(doctor);

        AccountEntity account = doctor.getAccount();
        if (account != null){
            account.setIsDeleted(true);
            account.setTrangThai(false);
            accountRepository.save(account);
        }
    }

    @Override
    public List<DoctorResponse> searchDoctor(DoctorSearchRequest request, String maPhongKham) {
        return doctorRepository.search(request, maPhongKham);
    }

    @Override
    public DoctorProjection getDetailDoctor(String maBacSi) {
        return doctorRepository.getDetail(maBacSi);
    }

    @Override
    public List<DoctorResponse> getAllDoctors(String maPhongKham) {
        return doctorRepository.findAllDoctors(maPhongKham);
    }
        
}
