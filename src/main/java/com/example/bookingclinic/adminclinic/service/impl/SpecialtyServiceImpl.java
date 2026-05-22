package com.example.bookingclinic.adminclinic.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookingclinic.adminclinic.dto.request.SpecialtyRequest;
import com.example.bookingclinic.adminclinic.dto.request.SpecialtySearchRequest;
import com.example.bookingclinic.adminclinic.entity.ClinicEntity;
import com.example.bookingclinic.adminclinic.entity.SpecialtyClinicEntity;
import com.example.bookingclinic.adminclinic.entity.SpecialtyClinicId;
import com.example.bookingclinic.adminclinic.entity.SpecialtyEntity;
import com.example.bookingclinic.adminclinic.repository.SpecialtyClinicRepository;
import com.example.bookingclinic.adminclinic.repository.SpecialtyRepository;
import com.example.bookingclinic.adminclinic.repository.projection.SpecialtyProjection;
import com.example.bookingclinic.adminclinic.service.SpecialtyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyClinicRepository specialtyClinicRepository;

    @Override
    public List<SpecialtyProjection> getAllSpecialty(String maPhongKham) {
        return specialtyRepository.findAllSpecialtyByClinicId(maPhongKham);
    }

    @Transactional
    @Override
    public SpecialtyProjection createSpecialty(SpecialtyRequest request, String maPhongKham) {
        String chuyenKhoa = generateMaChuyenKhoa();
        ClinicEntity clinicProxy = ClinicEntity.builder().maPhongKham(maPhongKham).build();
        
        SpecialtyEntity s = SpecialtyEntity.builder()
                .maChuyenKhoa(chuyenKhoa)
                .tenChuyenKhoa(request.getTenChuyenKhoa())
                .moTa(request.getMoTa())
                .trangThai(true)
                .ngayTao(LocalDateTime.now())
                .isDeleted(false)
                .build();
        specialtyRepository.save(s);

        SpecialtyClinicEntity sc = SpecialtyClinicEntity.builder()
                .id(new SpecialtyClinicId(maPhongKham, chuyenKhoa))
                .clinic(clinicProxy)
                .specialty(s)
                .build();
        specialtyClinicRepository.save(sc);
        return getSpecialtyDetail(maPhongKham, chuyenKhoa);
    }

    @Transactional
    @Override
    public SpecialtyProjection updateSpecialty(String maChuyenKhoa, SpecialtyRequest request, String maPhongKham) {
        SpecialtyEntity s = specialtyRepository.findByMaChuyenKhoaAndIsDeletedFalse(maChuyenKhoa)
                .orElseThrow(() -> new RuntimeException("Chuyên khoa không tồn tại"));

        s.setTenChuyenKhoa(request.getTenChuyenKhoa());
        s.setMoTa(request.getMoTa());

        specialtyRepository.save(s);
        return getSpecialtyDetail(maPhongKham, maChuyenKhoa);
    }

    @Transactional
    @Override
    public void deleteSpecialty(String maChuyenKhoa, String maPhongKham) {
        SpecialtyEntity s = specialtyRepository.findByMaChuyenKhoaAndIsDeletedFalse(maChuyenKhoa)
                .orElseThrow(() -> new RuntimeException("Chuyên khoa không tồn tại"));

        s.setIsDeleted(true);
        specialtyRepository.save(s);
    }

    public SpecialtyProjection getSpecialtyDetail(String maPhongKham, String maChuyenKhoa) {
        return specialtyRepository.findDetailByIdAndClinicId(maPhongKham, maChuyenKhoa)
                .orElseThrow(() -> new RuntimeException("Chuyên khoa không tồn tại"));
    }

    public List<SpecialtyProjection> searchSpecialty(SpecialtySearchRequest request, String maPhongKham) {
        return specialtyRepository.search(request, maPhongKham);
    }

    private String generateMaChuyenKhoa() {
        List<String> danhSachMaChuyenKhoa = specialtyRepository.findAllMaChuyenKhoa();
        int maxNumber = 0;
        for (String ma : danhSachMaChuyenKhoa) {
            if (ma != null && ma.startsWith("CK")) {
                try {
                    int currentNumber = Integer.parseInt(ma.substring(2));
                    if (currentNumber > maxNumber) {
                        maxNumber = currentNumber;
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
        int nextNumber = maxNumber + 1;
        return String.format("CK%02d", nextNumber);
    
    }
    
}
