package com.example.bookingclinic.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.entity.Doctor;
import com.example.bookingclinic.user.repository.UDoctorReponsitory;

@Service
public class UDoctorService {
    private UDoctorReponsitory doctorReponsitory;
    public UDoctorService(UDoctorReponsitory doctorReponsitory){
        this.doctorReponsitory = doctorReponsitory;
    }

    public Doctor getDoctorDetail(String id) {
        return doctorReponsitory.findById(id).orElse(null);
    }

    public Page<Doctor> getAllDoctorInCity(String tp, Pageable pageable) {
        return doctorReponsitory.findByPhongKham_TinhThanhPhoContainingIgnoreCase(tp, pageable);
    }

    public Page<Doctor> filterDoctorsByHocHamOrChuyenKhoa(String hh, String ck, String tp, Pageable pageable) {
        if (hh == "") {
            return doctorReponsitory.findBySpecialty_MaChuyenKhoaAndPhongKham_TinhThanhPhoContainingIgnoreCase(ck, tp,
                    pageable);
        }
        if (ck == "") {
            return doctorReponsitory.findByHocHamAndPhongKham_TinhThanhPhoContainingIgnoreCase(hh, tp, pageable);
        }
        return doctorReponsitory.findByHocHamAndSpecialty_MaChuyenKhoaAndPhongKham_TinhThanhPhoContainingIgnoreCase(hh,
                ck, tp, pageable);
    }

    public List<Doctor> searchDoctorWithAI(String specialty, String aiCity, String currentCity, int limit) {
        String finalCity = (aiCity == null || aiCity.isEmpty() || aiCity.equalsIgnoreCase("all")
                || aiCity.equalsIgnoreCase("value"))
                        ? currentCity
                        : aiCity;
        List<Doctor> result = doctorReponsitory
                .findBySpecialty_TenChuyenKhoaContainingIgnoreCaseAndPhongKham_TinhThanhPhoContainingIgnoreCase(
                        specialty, finalCity);

        return result.stream().limit(limit).toList();
    }
}
