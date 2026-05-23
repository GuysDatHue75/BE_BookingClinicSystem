package com.example.bookingclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
//@SpringBootApplication
// (exclude = { DataSourceAutoConfiguration.class })
@SpringBootApplication(scanBasePackages = "com.example.bookingclinic")
public class BookingclinicApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingclinicApplication.class, args);
    }
}
// 404 Not Found khi gọi /api/ cho thấy Spring Boot không tìm thấy Endpoint này
// Lỗi 400 nghĩa là Server nhận được yêu cầu nhưng nó không hiểu hoặc không chấp
// nhận vì thiếu dữ liệu bắt buộc.
