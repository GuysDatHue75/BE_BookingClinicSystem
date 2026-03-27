package com.example.bookingclinic.user.service;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.entity.Patient;
import com.example.bookingclinic.user.repository.UPatientRepository;

@Service
public class UPatientService {
    private UPatientRepository patientRepository;

    public UPatientService(UPatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public Patient getPatientById(String id){
        return patientRepository.findById(id).orElse(null);
    }

    public Patient editPatient(Patient newPatient, String id){
        return patientRepository.findById(id).map(oldPatient -> {
            oldPatient.setCanNang(newPatient.getCanNang());
            oldPatient.setChieuCao(newPatient.getChieuCao());
            oldPatient.setDiaChi(newPatient.getDiaChi());
            oldPatient.setEmail(newPatient.getEmail());
            oldPatient.setNhomMau(newPatient.getNhomMau());
            oldPatient.setTienSuBenhAn(newPatient.getTienSuBenhAn());
            oldPatient.setNgaySinh(newPatient.getNgaySinh());
            oldPatient.setGioiTinh(newPatient.getGioiTinh());
            oldPatient.setNgheNghiep(newPatient.getNgheNghiep());
            oldPatient.setQueQuan(newPatient.getQueQuan());
            oldPatient.setSoDienThoai(newPatient.getSoDienThoai());
            oldPatient.setTinhTrangSucKhoe(newPatient.getTinhTrangSucKhoe());
            return patientRepository.save(oldPatient);
        }).orElseThrow(() -> new RuntimeException("Không tìm thấy bậy nhân vói id: " + id));
    }
}
