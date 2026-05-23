package com.example.bookingclinic.adminsystem.repository.custom;

import org.springframework.data.domain.Page;

import com.example.bookingclinic.adminsystem.dto.request.SubscriptionPackageSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.SubscriptionPackageResponse;

public interface SubscriptionPackageRepositoryCustom {
    Page<SubscriptionPackageResponse> search(SubscriptionPackageSearchRequest request);

}
