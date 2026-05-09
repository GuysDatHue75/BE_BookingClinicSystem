package com.example.bookingclinic.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.entity.Package;
import com.example.bookingclinic.user.repository.PackageRepository;

@Service
public class PackageService {
    private PackageRepository packageRepository;
    public PackageService(PackageRepository packageRepository){
        this.packageRepository = packageRepository;
    }

    public List<Package> getAllPackage(){
        return packageRepository.findAll();
    }
}
