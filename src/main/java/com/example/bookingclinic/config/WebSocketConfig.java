package com.example.bookingclinic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Đổi thành /ws-chat để khớp với Frontend
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Cấu hình tiền tố cho các endpoint mà client gửi lên (vd: /app/chat)
        registry.setApplicationDestinationPrefixes("/app");

        // Bật broker cho các kênh:
        // - /topic: dùng cho broadcast chung (như /topic/public.status)
        // - /queue: dùng cho nhắn tin cá nhân 1-1
        registry.enableSimpleBroker("/topic", "/queue");

        // Cấu hình tiền tố cho user cụ thể (khi dùng convertAndSendToUser)
        registry.setUserDestinationPrefix("/user");
    }
}