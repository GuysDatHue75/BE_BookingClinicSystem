package com.example.bookingclinic.adminsystem.service;

import com.example.bookingclinic.adminsystem.dto.request.AccountRequest;
import com.example.bookingclinic.adminsystem.dto.request.AccountSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.AccountResponse;
import com.example.bookingclinic.adminsystem.dto.response.PageResponse;
import com.example.bookingclinic.adminsystem.repository.projection.AccountProjection;

public interface SystemAccountService {
    PageResponse<AccountResponse> searchAccounts(AccountSearchRequest request);
    AccountProjection getAccountDetail(String maTaiKhoan);
    String createAccount(AccountRequest request);
    void updateAccount(String maTaiKhoan, AccountRequest request);
    void deleteAccount(String maTaiKhoan);
}
