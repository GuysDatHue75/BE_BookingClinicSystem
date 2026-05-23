package com.example.bookingclinic.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    // Map lưu trữ: Key là userId, Value là status (true = online)

    public static final Map<String, Boolean> onlineUsers = new ConcurrentHashMap<>();

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        // Lấy userId từ header lúc connect
        String userId = (String) headerAccessor.getSessionAttributes().get("userId");

        if (userId != null) {
            onlineUsers.put(userId, true);
            log.info("Người dùng {} đã Online", userId);
            // Thông báo cho mọi người online
            broadcastStatus(userId, true);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = (String) headerAccessor.getSessionAttributes().get("userId");

        if (userId != null) {
            onlineUsers.remove(userId);
            log.info("Người dùng {} đã Offline", userId);
            broadcastStatus(userId, false);
        }
    }

    private void broadcastStatus(String userId, boolean isOnline) {
        Map<String, Object> statusMessage = new HashMap<>();
        statusMessage.put("userId", userId);
        statusMessage.put("online", isOnline);
        messagingTemplate.convertAndSend("/topic/public.status", statusMessage);
    }
}