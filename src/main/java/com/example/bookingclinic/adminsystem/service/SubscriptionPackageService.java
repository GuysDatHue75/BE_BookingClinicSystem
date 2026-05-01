package com.example.bookingclinic.adminsystem.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.bookingclinic.adminsystem.dto.request.SubscriptionPackageRequest;
import com.example.bookingclinic.adminsystem.dto.request.SubscriptionPackageSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.SubscriptionPackageResponse;

public interface SubscriptionPackageService {
    List<SubscriptionPackageResponse> getAllActivePackages();
    Page<SubscriptionPackageResponse> search(SubscriptionPackageSearchRequest request);
    Page<SubscriptionPackageResponse> filter(SubscriptionPackageSearchRequest request);
    void createPackage(SubscriptionPackageRequest request);
    void updatePackage(SubscriptionPackageRequest request);
    void deletePackage(String maGoi);

}
