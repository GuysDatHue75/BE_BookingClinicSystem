package com.example.bookingclinic.user.service;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.dto.UpdateAddressDTO;
import com.example.bookingclinic.user.entity.UAccount;
import com.example.bookingclinic.user.entity.UPatient;
import com.example.bookingclinic.user.repository.UPatientRepository;
import com.example.bookingclinic.user.repository.UAccountRepository;

import jakarta.transaction.Transactional;

@Service
public class UPatientService {
    private UPatientRepository patientRepository;
    private UAccountRepository uAccountRepository;
    public UPatientService(UPatientRepository patientRepository, UAccountRepository uAccountRepository){
        this.patientRepository = patientRepository;
        this.uAccountRepository = uAccountRepository;
    }

    public UPatient getPatientById(String id) {
        return patientRepository.findById(id).orElse(null);
    }

    public String updateQueQuan(String id, UpdateAddressDTO qq) {
        UPatient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) {
            return "Cập nhật thất bại";
        }
        patient.setQueQuan(qq.getQq());
        UPatient p = patientRepository.save(patient);
        if (p != null) {
            return "Cập nhật thành công";
        } else {
            return "Cập nhật thất bại";
        }
    }

    @Transactional
    public UPatient editPatient(String id, UPatient newPatient) {

        return patientRepository.findById(id).map(oldPatient -> {

            if (newPatient.getSoDienThoai() != null) {
                UAccount acc = oldPatient.getTaiKhoan();
                
                if (!newPatient.getSoDienThoai().equals(acc.getSoDt())) {
                    if (uAccountRepository.existsBySoDt(newPatient.getSoDienThoai())) {
                        throw new RuntimeException("Số điện thoại đã tồn tại!");
                    }
                    acc.setSoDt(newPatient.getSoDienThoai());
                }
                oldPatient.setSoDienThoai(newPatient.getSoDienThoai());
            }

            oldPatient.setCanNang(newPatient.getCanNang());
            oldPatient.getTaiKhoan().setHoVaTen(newPatient.getTaiKhoan().getHoVaTen());
            oldPatient.setChieuCao(newPatient.getChieuCao());
            oldPatient.setDiaChi(newPatient.getDiaChi());
            oldPatient.setEmail(newPatient.getEmail());
            oldPatient.setNhomMau(newPatient.getNhomMau());
            oldPatient.setTienSuBenhAn(newPatient.getTienSuBenhAn());
            oldPatient.setNgaySinh(newPatient.getNgaySinh());
            oldPatient.setGioiTinh(newPatient.getGioiTinh());
            oldPatient.setNgheNghiep(newPatient.getNgheNghiep());
            oldPatient.setQueQuan(newPatient.getQueQuan());
            oldPatient.setTinhTrangSucKhoe(newPatient.getTinhTrangSucKhoe());

            return patientRepository.save(oldPatient);

        }).orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân với id: " + id));
    }
}
