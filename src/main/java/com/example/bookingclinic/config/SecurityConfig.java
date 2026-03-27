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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
                                                .requestMatchers("/api/v1/**").permitAll()
                                                .anyRequest().authenticated())
                                .oauth2Login(oauth2 -> oauth2
                                                .successHandler((request, response, authentication) -> {
                                                        OAuth2User oauth2User = (OAuth2User) authentication
                                                                        .getPrincipal();

                                                        OAuth2AuthenticationToken authAndToken = (OAuth2AuthenticationToken) authentication;
                                                        String provider = authAndToken
                                                                        .getAuthorizedClientRegistrationId()
                                                                        .toUpperCase();
                                                        System.out.println(provider);

                                                        String email = oauth2User.getAttribute("email");
                                                        String name = oauth2User.getAttribute("name");
                                                        String providerId = "";
                                                        String picture = "";

                                                        if ("GOOGLE".equals(provider)) {
                                                                providerId = oauth2User.getAttribute("sub");
                                                                picture = oauth2User.getAttribute("picture");
                                                        } else if ("FACEBOOK".equals(provider)) {
                                                                providerId = oauth2User.getAttribute("id");
                                                                Map<String, Object> pictureObj = oauth2User
                                                                                .getAttribute("picture");
                                                                if (pictureObj != null) {
                                                                        Map<String, Object> dataObj = (Map<String, Object>) pictureObj
                                                                                        .get("data");
                                                                        picture = (String) dataObj.get("url");
                                                                }
                                                        }

                                                        String idPatient = "";
                                                        Patient existingPatient = patientRepository.findByEmail(email);

                                                        if (existingPatient == null) {
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

                                                                Account savedAccount = accountRepository
                                                                                .save(newAccount);

                                                                Patient newPatient = new Patient();
                                                                idPatient = "BN" + randomSuffix;
                                                                newPatient.setMaBenhNhan(idPatient);
                                                                newPatient.setEmail(email);
                                                                newPatient.setTaiKhoan(savedAccount);
                                                                patientRepository.save(newPatient);
                                                        } else {
                                                                idPatient = existingPatient.getMaBenhNhan();
                                                        }
                                                        response.sendRedirect(frontendUrl + "/login-success?role=BN&idPatient="+ idPatient);
                                                }));;
                return http.build();
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}