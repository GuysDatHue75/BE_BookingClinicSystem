package com.example.bookingclinic.adminclinic.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookingclinic.adminclinic.dto.request.DoctorRequest;
import com.example.bookingclinic.adminclinic.dto.request.DoctorSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.DoctorResponse;
import com.example.bookingclinic.adminclinic.entity.AccountEntity;
import com.example.bookingclinic.adminclinic.entity.DoctorEntity;
import com.example.bookingclinic.adminclinic.repository.AccountRepository;
import com.example.bookingclinic.adminclinic.repository.DoctorRepository;
import com.example.bookingclinic.adminclinic.repository.projection.DoctorProjection;
import com.example.bookingclinic.adminclinic.service.DoctorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final AccountRepository accountRepository;

    private synchronized String generateMaTaiKhoan() {
        Integer maxNumber = accountRepository.findMaxAccountIdNumber();
        int nextNumber = (maxNumber == null ? 0 : maxNumber) + 1;
        return String.format("TK%02d", nextNumber);
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

        AccountEntity account = AccountEntity.builder()
                .maTaiKhoan(maTaiKhoan)
                .soDt(request.getSoDienThoai())
                .matKhau("123456")
                .vaiTro("BacSi")
                .hoVaTen(request.getTenBacSi())
                .anhDaiDien(request.getAvt())
                .trangThai(true)
                .ngayTao(LocalDateTime.now())
                .ngayCapNhat(LocalDateTime.now())
                .maPhongKham(maPhongKham)
                .isDeleted(false)
                .build();
        accountRepository.save(account);

        DoctorEntity doctor = DoctorEntity.builder()
                .maBacSi(maBacSi)
                .tenBacSi(request.getTenBacSi())
                .gioiTinh(request.isGioiTinh())
                .soDienThoai(request.getSoDienThoai())
                .email(request.getEmail())
                .diaChi(request.getDiaChi())
                .avt(request.getAvt())
                .maChuyenKhoa(request.getMaChuyenKhoa())
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
                .maTaiKhoan(maTaiKhoan)
                .maPhongKham(maPhongKham)
                .ngayDangKy(LocalDateTime.now())
                .tepDinhKem(request.getTepDinhKem())
                .isDeleted(false)
                .build();
        doctorRepository.save(doctor);

        return maBacSi;
    }

    @Override
    @Transactional
    public void deleteDoctor(String maBacSi) {
        DoctorEntity doctor = doctorRepository.findById(maBacSi)
                .orElseThrow(() -> new RuntimeException("Bác sĩ không tồn tại"));

        doctor.setIsDeleted(true);
        doctorRepository.save(doctor);

        AccountEntity account = accountRepository.findById(doctor.getMaTaiKhoan())
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

        account.setIsDeleted(true);
        account.setTrangThai(false);
        accountRepository.save(account);
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
