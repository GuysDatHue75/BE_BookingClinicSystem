package com.example.bookingclinic.config;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import com.example.bookingclinic.user.entity.UAccount;
import com.example.bookingclinic.user.entity.UPatient;
import com.example.bookingclinic.user.repository.UAccountRepository;
import com.example.bookingclinic.user.repository.UPatientRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        @Autowired
        private UPatientRepository patientRepository;
        @Autowired
        private UAccountRepository accountRepository;
        @Value("${app.frontend.url}")
        private String frontendUrl;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                // 1. Cấu hình CORS bằng Lambda mới
                                .cors(cors -> cors.configure(http))
                                // 2. Tắt CSRF
                                .csrf(csrf -> csrf.disable())
                                // 3. Cấu hình phân quyền Request
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/stringee/**").permitAll()
                                                .requestMatchers("/api/v1/**").permitAll()
                                                // MỞ KHÓA WEBSOCKET: Thêm dòng này để cho phép socket đi qua
                                                .requestMatchers("/ws-chat/**").permitAll()
                                                .requestMatchers("/uploads/**").permitAll()
                                                .anyRequest().authenticated())
                                // 4. ĐÂY LÀ ĐOẠN FIX LỖI: Tắt X-Frame-Options theo cú pháp Lambda mới
                                .headers(headers -> headers
                                                .frameOptions(frame -> frame.disable()))
                                // 5. Cấu hình OAuth2 Login (Giữ nguyên logic xử lý của nhóm sếp)
                                .oauth2Login(oauth2 -> oauth2
                                                .successHandler((request, response, authentication) -> {
                                                        OAuth2User oauth2User = (OAuth2User) authentication
                                                                        .getPrincipal();

                                                        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
                                                        String provider = authToken.getAuthorizedClientRegistrationId()
                                                                        .toUpperCase();

                                                        String email = oauth2User.getAttribute("email");
                                                        String name = oauth2User.getAttribute("name");
                                                        String providerId = "";
                                                        String picture = "";

                                                        if ("GOOGLE".equals(provider)) {
                                                                providerId = oauth2User.getAttribute("sub");
                                                                picture = oauth2User.getAttribute("picture");
                                                        } else if ("FACEBOOK".equals(provider)) {
                                                                providerId = oauth2User.getAttribute("id");

                                                                if (email == null) {
                                                                        email = providerId + "@facebook.com";
                                                                }

                                                                Map<String, Object> pictureObj = oauth2User
                                                                                .getAttribute("picture");
                                                                if (pictureObj != null) {
                                                                        Map<String, Object> dataObj = (Map<String, Object>) pictureObj
                                                                                        .get("data");
                                                                        picture = (String) dataObj.get("url");
                                                                }
                                                        }

                                                        String idPatient = "";
                                                        String idAccount = "";
                                                        UAccount account = accountRepository
                                                                        .findByProviderAndProviderId(provider,
                                                                                        providerId);

                                                        if (account == null) {
                                                                String randomSuffix = UUID.randomUUID().toString()
                                                                                .substring(0, 8);

                                                                UAccount newAccount = new UAccount();
                                                                newAccount.setMaTaiKhoan("TK" + randomSuffix);
                                                                newAccount.setHoVaTen(name);
                                                                newAccount.setAnhDaiDien(picture);
                                                                newAccount.setVaiTro("BenhNhan");
                                                                newAccount.setTrangThai(true);
                                                                newAccount.setNgayTao(LocalDateTime.now());
                                                                newAccount.setProvider(provider);
                                                                newAccount.setProviderId(providerId);
                                                                newAccount.setMatKhau("OAUTH2_USER");
                                                                newAccount.setLanDauDangNhap(1);

                                                                UAccount savedAccount = accountRepository
                                                                                .save(newAccount);

                                                                UPatient newPatient = new UPatient();
                                                                idPatient = "BN" + randomSuffix;
                                                                newPatient.setMaBenhNhan(idPatient);
                                                                newPatient.setEmail(email);
                                                                newPatient.setTaiKhoan(savedAccount);
                                                                patientRepository.save(newPatient);
                                                                idAccount = savedAccount.getMaTaiKhoan();

                                                        } else {
                                                                UPatient patient = patientRepository
                                                                                .findByTaiKhoan(account);
                                                                idPatient = patient.getMaBenhNhan();
                                                                idAccount = account.getMaTaiKhoan();
                                                        }
                                                        response.sendRedirect(frontendUrl
                                                                        + "/login-success?role=BenhNhan&idPatient="
                                                                        + idPatient + "&idAccount=" + idAccount);
                                                }));

                return http.build();
        }
}