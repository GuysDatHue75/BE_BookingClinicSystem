package com.example.bookingclinic.adminsystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;

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

@Service
@RequiredArgsConstructor
public class BrowseClinicServiceImpl implements BrowseClinicService {

    private final BrowseClinicRepository browseClinicRepository;
    private final ClinicRepository clinicRepository;
    private final BrowseClinicMapper browseClinicMapper;
    private final SubscriptionPackageRepository subscriptionPackageRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<BrowseClinicResponse> getAll() {
        return browseClinicMapper.toResponseListBrowseClinic(browseClinicRepository.findAll());
    }

    @Override
    public List<BrowseClinicResponse> search(BrowseClinicSearchRequest request) {
        List<BrowseClinicEntity> result = browseClinicRepository.searchBrowseClinic(request);
        return browseClinicMapper.toResponseListBrowseClinic(result);
    }

    @Override
    public List<BrowseClinicResponse> getPending() {
        return browseClinicMapper.toResponseListBrowseClinic(
            browseClinicRepository.findByTrangThaiOrderByNgayDangKyDesc("Chờ duyệt")
        );
    }

    private synchronized String generateMaTaiKhoan() {
        return "TK" + System.currentTimeMillis();
    }

    public void approve(BrowseClinicEntity entity) {
        
        SubscriptionPackageEntity goi = subscriptionPackageRepository.findById(entity.getMaGoi())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy gói với mã: " + entity.getMaGoi()));
        int thoiGianNgay = goi.getThoiHanNgay();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ngayHetHan = now.plusDays(thoiGianNgay);
        String maTaiKhoan = generateMaTaiKhoan();

        AccountEntity account = AccountEntity.builder()
                .maTaiKhoan(maTaiKhoan)
                .soDt(entity.getSoDienThoai())
                .matKhau("123456")
                .vaiTro("BacSi")
                .hoVaTen(entity.getTenPhongKham())
                .anhDaiDien(entity.getAnhPhongKham())
                .trangThai(true)
                .ngayTao(LocalDateTime.now())
                .ngayCapNhat(LocalDateTime.now())
                .isDeleted(false)
                .build();
        accountRepository.save(account);


        ClinicEntity clinic = ClinicEntity.builder()
                .maPhongKham(entity.getMaPhongKham())
                .tenPhongKham(entity.getTenPhongKham())
                .maChuyenKhoa(entity.getMaChuyenKhoa())
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
                .tepDinhKem(entity.getTepDinhKem())
                .nguoiDaiDien(entity.getNguoiDaiDien())
                .soDienThoaiNguoiDaiDien(entity.getSoDienThoaiNguoiDaiDien())
                .loaiHinhPhongKham(entity.getLoaiHinhPhongKham())
                .diaChi(entity.getDiaChi())
                .tinhThanhPho(entity.getTinhThanhPho())
                .soLuongBacSi(entity.getSoLuongBacSi())
                .trangThai("Hoạt động")
                .maGoi(entity.getMaGoi())
                .isDeleted(false)
                .maTaiKhoan(maTaiKhoan)
                .anhPhongKham(entity.getAnhPhongKham())
                .build();

        clinicRepository.save(clinic);

        entity.setTrangThai("Đã duyệt");
        browseClinicRepository.save(entity);
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
    public List<BrowseClinicResponse> filter(BrowseClinicSearchRequest request) {
        List<BrowseClinicEntity> result = browseClinicRepository.findAll();

        if(request.getTrangThai() != null && !request.getTrangThai().isEmpty()) {
            result = result.stream()
                .filter(c -> request.getTrangThai().equalsIgnoreCase(c.getTrangThai()))
                .toList();
        }

        return browseClinicMapper.toResponseListBrowseClinic(result);
    }

    @Override
    public BrowseClinicDetailResponse getDetail(String maPhongKham) {
        BrowseClinicEntity entity = browseClinicRepository.findById(maPhongKham)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng khám với mã: " + maPhongKham));

        return browseClinicMapper.toDetailResponseFromBrowseClinic(entity);
    }
}