package com.example.bookingclinic.doctor.controller.Chat;

import com.example.bookingclinic.doctor.dto.Chat.ChatMessageDTO;
import com.example.bookingclinic.doctor.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomService chatRoomService;

    // React will send messages to this endpoint: /app/chat
    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageDTO chatMessageDTO) {
        // 1. Lưu DB
        ChatMessageDTO savedMessage = chatRoomService.saveMessage(chatMessageDTO);

        // 2. Bắn cho người nhận qua kênh /topic/messages/{ID_Người_Nhận}
        messagingTemplate.convertAndSend(
                "/topic/messages/" + chatMessageDTO.getMaNguoiNhan(),
                savedMessage);

        // 3. Bắn ngược lại cho người gửi qua kênh /topic/messages/{ID_Người_Gửi}
        messagingTemplate.convertAndSend(
                "/topic/messages/" + chatMessageDTO.getMaNguoiGui(),
                savedMessage);
    }

}