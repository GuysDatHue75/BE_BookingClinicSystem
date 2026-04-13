package com.example.bookingclinic.adminsystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.adminsystem.dto.request.BrowseDoctorActionRequest;
import com.example.bookingclinic.adminsystem.dto.request.BrowseDoctorSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.BrowseDoctorDetailResponse;
import com.example.bookingclinic.adminsystem.entity.AccountEntity;
import com.example.bookingclinic.adminsystem.entity.BrowseDoctorEntity;
import com.example.bookingclinic.adminsystem.entity.DoctorEntity;
import com.example.bookingclinic.adminsystem.repository.AccountRepository;
import com.example.bookingclinic.adminsystem.repository.BrowseDoctorRepository;
import com.example.bookingclinic.adminsystem.repository.DoctorRepository;
import com.example.bookingclinic.adminsystem.repository.projection.BrowseDoctorProjection;
import com.example.bookingclinic.adminsystem.service.BrowseDoctorService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrowseDoctorServiceImpl implements BrowseDoctorService{

    private final BrowseDoctorRepository browseDoctorRepository;
    private final AccountRepository accountRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public List<BrowseDoctorProjection> search(BrowseDoctorSearchRequest request) {
        return browseDoctorRepository.search(request);
    }
    
    private String generateMaTaiKhoan(){
        String prefix = "TK";
        String random = String.valueOf(System.currentTimeMillis()).substring(7);
        return prefix + random;
    }

    public void approve(BrowseDoctorEntity entity) {

        if(accountRepository.existsBySoDt(entity.getSoDienThoai())){
            throw new RuntimeException("Số điện thoại đã tồn tại tài khoản");
        }
        String maTaiKhoan = generateMaTaiKhoan();

        AccountEntity account = AccountEntity.builder()
            .maTaiKhoan(maTaiKhoan)
            .soDt(entity.getSoDienThoai())
            .matKhau("123456")
            .vaiTro("Doctor")
            .hoVaTen(entity.getTenBacSi())
            .anhDaiDien(entity.getAvt())
            .trangThai(true)
            .ngayTao(LocalDateTime.now())
            .ngayCapNhat(LocalDateTime.now())
            .maPhongKham(entity.getMaPhongKham())
            .build();
            
        accountRepository.save(account);

        DoctorEntity doctor = DoctorEntity.builder()
            .maBacSi(entity.getMaBacSi())
            .tenBacSi(entity.getTenBacSi())
            .gioiTinh(entity.isGioiTinh())
            .soDienThoai(entity.getSoDienThoai())
            .email(entity.getEmail())
            .diaChi(entity.getDiaChi())
            .avt(entity.getAvt())
            .chuyenKhoa(entity.getChuyenKhoa())
            .bangCap(entity.getBangCap())
            .kinhNghiem(entity.getKinhNghiem())
            .hoatDong(entity.getHoatDong())
            .mieuTa(entity.getMieuTa())
            .chucVu(entity.getChucVu())
            .hocHam(entity.getHocHam())
            .cccd(entity.getCccd())
            .soGiayPhep(entity.getSoGiayPhep())
            .ngayCap(entity.getNgayCap())
            .noiCap(entity.getNoiCap())
            .maPhongKham(entity.getMaPhongKham())
            .maTaiKhoan(maTaiKhoan)
            .ngayDangKy(entity.getNgayDangKy())
            .tepDinhKem(entity.getTepDinhKem())
            .build();

        doctorRepository.save(doctor);

        entity.setTrangThai("Đã duyệt");
        browseDoctorRepository.save(entity);

    }

    public void reject(BrowseDoctorEntity entity, String lyDoTuChoi) {
        entity.setTrangThai("Đã từ chối");
        entity.setLyDoTuChoi(lyDoTuChoi);
        browseDoctorRepository.save(entity);
    }

    @Override
    @Transactional
    public void handleBrowseDoctor(BrowseDoctorActionRequest request) {
        BrowseDoctorEntity entity = browseDoctorRepository.findById(request.getMaBacSi())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bác sĩ với mã: " + request.getMaBacSi()));
        if(Boolean.TRUE.equals(request.getIsApproved())){
            approve(entity);
        } else {
            reject(entity, request.getLyDoTuChoi());
        }
    }


    @Override
    public BrowseDoctorDetailResponse getDetail(String maBacSi){
        return browseDoctorRepository.getDetail(maBacSi);
    }
}
