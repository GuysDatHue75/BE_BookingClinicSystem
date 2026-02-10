package com.example.bookingclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class BookingclinicApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingclinicApplication.class, args);
    }
}