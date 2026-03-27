package com.example.bookingclinic.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.dto.UpdateImageDTO;
import com.example.bookingclinic.user.entity.Account;
import com.example.bookingclinic.user.repository.UAccountRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService {
    
    @Autowired
    private UAccountRepository accountRepository;

    @Transactional
    public Account updateImage(UpdateImageDTO infors){
        Account exitsAccount = accountRepository.findById(infors.getMaTaiKhoan()).orElse(null);

        if(infors.getAnhBase64() != null && !infors.getAnhBase64().isEmpty()){
            if (!infors.getAnhBase64().startsWith("data:image/")) {
                throw new RuntimeException("Định dạng ảnh không hợp lệ");
            }
            double sizeInBytes = (infors.getAnhBase64().length() * 3.0) / 4.0;
            if (sizeInBytes > 2 * 1024 * 1024) { 
                throw new RuntimeException("Kích thước ảnh quá lớn (tối đa 2MB)");
            }
            exitsAccount.setAnhDaiDien(infors.getAnhBase64());
        }
        return accountRepository.save(exitsAccount);
    }
}
