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

import com.example.bookingclinic.user.entity.Account;
import com.example.bookingclinic.user.entity.Patient;
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
                                .cors()
                                .and()
                                .csrf().disable()
                                .authorizeHttpRequests(auth -> auth
                                        .requestMatchers("/api/stringee/**").permitAll()
                                                .requestMatchers("/api/v1/**").permitAll()
                                                // DÒNG THÊM MỚI: Mở khóa cho toàn bộ API bắt đầu bằng /api/stringee/
                                                .anyRequest().authenticated())
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
                                                        int isOneLogin;
                                                        String idAccount = "";
                                                        Account account = accountRepository.findByProviderAndProviderId(
                                                                        provider, providerId);

                                                        if (account == null) {
                                                                String randomSuffix = UUID.randomUUID().toString()
                                                                                .substring(0, 8);

                                                                Account newAccount = new Account();
                                                                newAccount.setMaTaiKhoan("TK" + randomSuffix);
                                                                newAccount.setHoVaTen(name);
                                                                newAccount.setAnhDaiDien(picture);
                                                                newAccount.setVaiTro("BN");
                                                                newAccount.setTrangThai(true);
                                                                newAccount.setNgayTao(LocalDateTime.now());
                                                                newAccount.setProvider(provider);
                                                                newAccount.setProviderId(providerId);
                                                                newAccount.setMatKhau("OAUTH2_USER");
                                                                newAccount.setLanDauDangNhap(1);

                                                                Account savedAccount = accountRepository
                                                                                .save(newAccount);

                                                                Patient newPatient = new Patient();
                                                                idPatient = "BN" + randomSuffix;
                                                                newPatient.setMaBenhNhan(idPatient);
                                                                newPatient.setEmail(email);
                                                                newPatient.setTaiKhoan(savedAccount);
                                                                patientRepository.save(newPatient);
                                                                isOneLogin = 1;
                                                                idAccount = savedAccount.getMaTaiKhoan();

                                                        } else {
                                                                Patient patient = patientRepository
                                                                                .findByTaiKhoan(account);

                                                                idPatient = patient.getMaBenhNhan();
                                                                idAccount = account.getMaTaiKhoan();
                                                        }
                                                        response.sendRedirect(frontendUrl +
                                                                        "/login-success?role=BN&idPatient=" + idPatient
                                                                        + "&idAccount=" + idAccount);

                                                }));
                return http.build();
        }
}