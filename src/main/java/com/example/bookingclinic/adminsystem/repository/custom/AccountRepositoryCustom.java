package com.example.bookingclinic.adminsystem.repository.custom;

import com.example.bookingclinic.adminsystem.dto.request.AccountSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.AccountResponse;
import com.example.bookingclinic.adminsystem.dto.response.PageResponse;
import com.example.bookingclinic.adminsystem.repository.projection.AccountProjection;

public interface AccountRepositoryCustom {
    PageResponse<AccountResponse> search(AccountSearchRequest request);
    AccountProjection getDetail(String maTaiKhoan);
}
