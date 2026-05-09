package com.example.bookingclinic.user.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookingclinic.user.entity.UNews;

public interface UNewsRepository extends JpaRepository<UNews, String> {
    List<UNews> findByPhongKham_MaPhongKham(String id);
    UNews findByMaTinTuc(String id);
     Page<UNews> findByPhongKham_TinhThanhPhoContainingIgnoreCase(String tp ,Pageable pageable);
}
