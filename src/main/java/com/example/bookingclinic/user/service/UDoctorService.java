package com.example.bookingclinic.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.entity.Doctor;
import com.example.bookingclinic.user.repository.UDoctorReponsitory;

@Service
public class UDoctorService {
    private UDoctorReponsitory doctorReponsitory;

    public UDoctorService(UDoctorReponsitory doctorReponsitory){
        this.doctorReponsitory = doctorReponsitory;
    }

    public Doctor getDoctorDetail(String id){
        return doctorReponsitory.findById(id).orElse(null);
    }

    public List<Doctor> getAllDoctorInCity(String tp){
        return doctorReponsitory.findByPhongKham_TinhThanhPhoContainingIgnoreCase(tp);
    }

    public List<Doctor> filterDoctorsByHocHamOrChuyenKhoa(String hh, String ck,String tp){
        if(hh == ""){
            return doctorReponsitory.findBySpecialty_MaChuyenKhoaAndPhongKham_TinhThanhPhoContainingIgnoreCase(ck, tp);
        }
        if(ck == ""){
            return doctorReponsitory.findByHocHamAndPhongKham_TinhThanhPhoContainingIgnoreCase(hh, tp);
        }
        return doctorReponsitory.findByHocHamAndSpecialty_MaChuyenKhoaAndPhongKham_TinhThanhPhoContainingIgnoreCase(hh, ck, tp);
    }

    public List<Doctor> searchDoctorWithAI(String name, String tp, int limit) {

        List<Doctor> result;

        result = doctorReponsitory.findBySpecialty_TenChuyenKhoaContainingIgnoreCaseAndPhongKham_TinhThanhPhoContainingIgnoreCase(name,"Thừa Thiên Huế");

        return result.stream().limit(limit).toList();
    }
}
