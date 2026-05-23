package com.example.bookingclinic.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookingclinic.user.entity.Notification;

public interface UNotificationRepository extends JpaRepository<Notification, String> {

}
