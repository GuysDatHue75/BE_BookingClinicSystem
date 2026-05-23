package com.example.bookingclinic.config; 

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class CaptchaConfig {

    @Bean
    public DefaultKaptcha defaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        
        // Sếp có thể tùy chỉnh các thông số này theo ý thích
        properties.setProperty("kaptcha.border", "yes"); // Có viền không?
        properties.setProperty("kaptcha.border.color", "105,179,90"); // Màu viền
        properties.setProperty("kaptcha.textproducer.font.color", "black"); // Màu chữ
        properties.setProperty("kaptcha.image.width", "160"); // Chiều rộng ảnh
        properties.setProperty("kaptcha.image.height", "50"); // Chiều cao ảnh
        properties.setProperty("kaptcha.textproducer.font.size", "40"); // Size chữ
        properties.setProperty("kaptcha.session.key", "captchaCode"); 
        properties.setProperty("kaptcha.textproducer.char.length", "5"); // Số lượng ký tự (VD: 5 chữ)
        properties.setProperty("kaptcha.textproducer.font.names", "Arial,Courier"); // Font chữ

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        
        return defaultKaptcha;
    }
}