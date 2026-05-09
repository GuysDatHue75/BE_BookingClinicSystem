package com.example.bookingclinic.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.entity.Package;
import com.example.bookingclinic.user.service.PackageService;

@RestController
@RequestMapping("/api/v1")
public class PackageController {
    private PackageService packageService;
    public PackageController(PackageService packageService){
        this.packageService = packageService;
    }

    @GetMapping("/packages")
    public List<Package> getAllPackage(){
        return packageService.getAllPackage();
    }
}
