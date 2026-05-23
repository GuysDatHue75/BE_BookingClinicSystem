package com.example.bookingclinic.auth.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.bookingclinic.auth.dto.UserDTO;
import com.example.bookingclinic.user.entity.UAccount;
import com.example.bookingclinic.user.repository.UAccountRepository;
import com.example.bookingclinic.user.repository.UDoctorReponsitory;
import com.example.bookingclinic.user.repository.UPatientRepository;

import com.example.bookingclinic.adminclinic.repository.ClinicRepository;

// import com.example.bookingclinic.user.entity.Account;
// import com.example.bookingclinic.user.repository.AccountRepository;
// import com.example.bookingclinic.user.repository.ClinicRepository;
// import com.example.bookingclinic.user.repository.DoctorReponsitory;

// import com.example.bookingclinic.doctor.entity.Account;
// import com.example.bookingclinic.doctor.repository.AccountRepository;
// import com.example.bookingclinic.doctor.repository.DoctorRepository;
// import com.example.bookingclinic.doctor.repository.PatientRepository;

@Service
public class LoginService {

    private UAccountRepository accountRepository;
    private UPatientRepository patientRepository;
    private UDoctorReponsitory doctorRepository;
    private ClinicRepository clinicRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public LoginService(
            UAccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            UPatientRepository patientsRepository,
            UDoctorReponsitory doctorRepository,
            ClinicRepository clinicRepository) {
        this.accountRepository = accountRepository;
        this.clinicRepository = clinicRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientsRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public ResponseEntity<?> login(UserDTO infors) {
        UAccount user = accountRepository.findBySoDt(infors.getPhone()) .orElseThrow(() -> new RuntimeException("Không tìm thấy số điện thoại"));;
        if (user == null) {
            return ResponseEntity.status(404).body("Số điện thoại không tồn tại!");
        }
        if (passwordEncoder.matches(infors.getPass(), user.getMatKhau())) {
        // if(infors.getPass().equals(user.getMatKhau())){
            Object profileData = null;
            if ("BenhNhan".equals(user.getVaiTro())) {
                profileData = patientRepository.findByTaiKhoan_MaTaiKhoan(user.getMaTaiKhoan());
            } else if ("BacSi".equals(user.getVaiTro())) {
                profileData = doctorRepository.findByTaiKhoan_MaTaiKhoan(user.getMaTaiKhoan());
            } else if ("PhongKham".equals(user.getVaiTro())) {
                profileData = clinicRepository.findByAccount_MaTaiKhoan(user.getMaTaiKhoan());
            }else if("Admin".equals(user.getVaiTro())){
            }
            if (profileData == null) {
                return ResponseEntity.status(404).body("Không tìm thấy thông tin chi tiết người dùng.");
            }
            return ResponseEntity.ok(profileData);
        } else {
            return ResponseEntity.status(401).body("Mật khẩu không đúng!");
        }
    }
}
