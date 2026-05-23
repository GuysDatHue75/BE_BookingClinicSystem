package com.example.bookingclinic.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.dto.FileImageDTO;
import com.example.bookingclinic.user.dto.MedicalHistoryDTO;
import com.example.bookingclinic.user.dto.PrescriptionItemDTO;
import com.example.bookingclinic.user.entity.Calendar;
import com.example.bookingclinic.user.entity.Doctor;
import com.example.bookingclinic.user.entity.File;
import com.example.bookingclinic.user.entity.FileImage;
import com.example.bookingclinic.user.entity.Prescription;
import com.example.bookingclinic.user.entity.UPrescriptionDetail;
import com.example.bookingclinic.user.repository.CalendarRepository;
import com.example.bookingclinic.user.repository.FileRepository;
import com.example.bookingclinic.user.repository.UPrescriptionRepository;

@Service
public class UPrescriptionService {
    private UPrescriptionRepository prescriptionRepository;
    private CalendarRepository calendarRepository;
    private FileRepository fileRepository;

    public UPrescriptionService(UPrescriptionRepository prescriptionRepository, CalendarRepository calendarRepository, FileRepository fileRepository){
        this.prescriptionRepository = prescriptionRepository;
        this.calendarRepository = calendarRepository;
        this.fileRepository = fileRepository;
    }

    public List<MedicalHistoryDTO> getMedicalHistory(String maBenhNhan) {

        List<Calendar> calendars = calendarRepository
                .findByPatient_MaBenhNhanAndTrangThaiOrderByNgayKhamDesc(
                        maBenhNhan,
                        "DaKham");

        List<MedicalHistoryDTO> result = new ArrayList<>();

        for (Calendar calendar : calendars) {

            MedicalHistoryDTO dto = new MedicalHistoryDTO();

            // lịch khám
            dto.setMaLichKham(calendar.getMaLichKham());
            dto.setNgayKham(calendar.getNgayKham());
            dto.setTrangThai(calendar.getTrangThai());
            dto.setDanhGia(calendar.getDanhGia());

            // bác sĩ
            Doctor doctor = calendar.getBacSi();

            if (doctor != null) {

                dto.setMaBacSi(doctor.getMaBacSi());

                if (doctor.getTaiKhoan() != null) {
                    dto.setTenBacSi(
                            doctor.getTaiKhoan().getHoVaTen());
                }

                dto.setHocHam(doctor.getHocHam());

                // phòng khám
                if (doctor.getPhongKham() != null) {
                    dto.setMaPhongKham(
                            doctor.getPhongKham().getMaPhongKham());
                    dto.setTenPhongKham(
                            doctor.getPhongKham().getTenPhongKham());
                    dto.setDiaChiPhongKham(
                            doctor.getPhongKham().getDiaChi());
                    dto.setSdtPhongKham(
                            doctor.getPhongKham().getSoDienThoai());
                }

                // chuyên khoa
                if (doctor.getSpecialty() != null) {

                    dto.setTenChuyenKhoa(
                            doctor.getSpecialty().getTenChuyenKhoa());
                }
            }

            // hồ sơ
            Optional<File> optionalFile = fileRepository.findByCalendar_MaLichKham(
                    calendar.getMaLichKham());

            if (optionalFile.isPresent()) {

                File file = optionalFile.get();

                dto.setTrieuChung(file.getTrieuChung());
                dto.setChuanDoan(file.getChuanDoan());
                dto.setKetLuan(file.getKetLuan());
                dto.setGhiChu(file.getGhiChu());
                dto.setNgayLapHoSo(file.getNgayLap());

                // ảnh
                List<FileImageDTO> images = new ArrayList<>();

                if (file.getListImage() != null) {

                    for (FileImage img : file.getListImage()) {
                        images.add(new FileImageDTO(img.getTenAnh(), img.getDuongDanAnh()));
                    }
                }

                dto.setImages(images);


                // đơn thuốc
                Optional<Prescription> optionalPrescription = prescriptionRepository.findByFile_MaHoSo(
                        file.getMaHoSo());

                if (optionalPrescription.isPresent()) {
                    Prescription prescription = optionalPrescription.get();
                    dto.setMaSoDonThuoc(prescription.getMaSoDonThuoc());
                    dto.setNgayLapDon(prescription.getNgayLap());
                    List<PrescriptionItemDTO> items = new ArrayList<>();
                    if (prescription.getDanhSachThuoc() != null) {
                        for (UPrescriptionDetail d : prescription.getDanhSachThuoc()) {
                            items.add(
                                    new PrescriptionItemDTO(
                                            d.getTenThuoc(),
                                            d.getLieuDung(),
                                            d.getSoLuong(),
                                            d.getDonGia(),
                                            d.getDonVi(),
                                            d.getGhiChu()));
                        }
                    }

                    dto.setDanhSachThuoc(items);
                }
            }
            result.add(dto);
        }
        return result;
    }
}
