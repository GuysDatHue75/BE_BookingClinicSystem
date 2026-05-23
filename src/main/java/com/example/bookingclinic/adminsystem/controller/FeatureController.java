package com.example.bookingclinic.adminsystem.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.adminsystem.dto.response.FeaturesDto;
import com.example.bookingclinic.adminsystem.repository.FeaturesRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/adminsystem/features")
@RequiredArgsConstructor
public class FeatureController {
    private final FeaturesRepository featuresRepository;

    // API này Frontend dùng để vẽ ra tất cả các Checkbox trên Form
    @GetMapping
    public List<FeaturesDto> getAllFeatures() {
        return featuresRepository.findAll().stream()
                .map(f -> new FeaturesDto(f.getMaTinhNang(), f.getTenTinhNang()))
                .collect(Collectors.toList());
    }
}
