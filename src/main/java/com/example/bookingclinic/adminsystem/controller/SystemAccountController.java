package com.example.bookingclinic.adminsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminsystem.dto.request.AccountRequest;
import com.example.bookingclinic.adminsystem.dto.request.AccountSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.AccountResponse;
import com.example.bookingclinic.adminsystem.dto.response.PageResponse;
import com.example.bookingclinic.adminsystem.repository.projection.AccountProjection;
import com.example.bookingclinic.adminsystem.service.SystemAccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/adminsystem/account")
@RequiredArgsConstructor
public class SystemAccountController {
     private final SystemAccountService systemAccountService;
 
    /**
     * Tìm kiếm + phân trang.
     * POST /api/v1/adminsystem/account/search
     * Body: { keyword, vaiTro, page, size }
     */
    @PostMapping("/search")
    public ResponseEntity<PageResponse<AccountResponse>> searchAccounts(
            @RequestBody AccountSearchRequest request) {
        return ResponseEntity.ok(systemAccountService.searchAccounts(request));
    }
 
    /**
     * Chi tiết tài khoản.
     * GET /api/v1/adminsystem/account/detail/{maTaiKhoan}
     */
    @GetMapping("/detail/{maTaiKhoan}")
    public ResponseEntity<AccountProjection> getAccountDetail(
            @PathVariable String maTaiKhoan) {
        return ResponseEntity.ok(systemAccountService.getAccountDetail(maTaiKhoan));
    }
 
    /**
     * Tạo tài khoản.
     * POST /api/v1/adminsystem/account/create
     */
    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<String> createAccount(@ModelAttribute AccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(systemAccountService.createAccount(request));
    }
 
    /**
     * Cập nhật tài khoản.
     * PUT /api/v1/adminsystem/account/update/{maTaiKhoan}
     */
    // @PutMapping(value = "/update/{maTaiKhoan}", consumes = {"multipart/form-data"})
    @PostMapping(value = "/update/{maTaiKhoan}", consumes = {"multipart/form-data"})
    public ResponseEntity<String> updateAccount(
            @PathVariable String maTaiKhoan,
            @ModelAttribute AccountRequest request) {
        systemAccountService.updateAccount(maTaiKhoan, request);
        return ResponseEntity.ok("Cập nhật tài khoản thành công");
    }
 
    /**
     * Xóa mềm tài khoản.
     * DELETE /api/v1/adminsystem/account/delete/{maTaiKhoan}
     */
    @DeleteMapping("/delete/{maTaiKhoan}")
    public ResponseEntity<String> deleteAccount(@PathVariable String maTaiKhoan) {
        systemAccountService.deleteAccount(maTaiKhoan);
        return ResponseEntity.ok("Xóa tài khoản thành công");
    }
}
