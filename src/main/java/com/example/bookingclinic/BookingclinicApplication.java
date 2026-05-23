package com.example.bookingclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;








@SpringBootApplication(scanBasePackages = "com.example.bookingclinic")
@EnableScheduling

public class BookingclinicApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingclinicApplication.class, args);
    }

}