package com.example.bookingclinic.auth.service;

import com.example.bookingclinic.auth.dto.CaptchaResponseDTO;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Service
public class CaptchaService {

    private final DefaultKaptcha kaptcha;
    private final Map<String, String> captchaStore = new ConcurrentHashMap<>();

    public CaptchaService(DefaultKaptcha kaptcha) {
        this.kaptcha = kaptcha;
    }

    public CaptchaResponseDTO generateCaptcha() throws Exception {
        String text = kaptcha.createText();
        BufferedImage image = kaptcha.createImage(text);

        String captchaId = UUID.randomUUID().toString();
        captchaStore.put(captchaId, text);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);

        String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());

        return new CaptchaResponseDTO(captchaId, base64);
    }

    public boolean validateCaptcha(String captchaId, String input) {
        String real = captchaStore.get(captchaId);

        if (real == null)
            return false;

        boolean result = real.equalsIgnoreCase(input);
        captchaStore.remove(captchaId);

        return result;
    }
}