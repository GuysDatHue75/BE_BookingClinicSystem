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
        // 1. Save message to DB
        ChatMessageDTO savedMessage = chatRoomService.saveMessage(chatMessageDTO);

        // 2. Push message to the recipient via WebSocket
        // Recipient's listening channel: /user/{recipient_id}/queue/messages
        messagingTemplate.convertAndSendToUser(
                chatMessageDTO.getMaNguoiNhan(),
                "/queue/messages",
                savedMessage);
    }

    // Endpoint để thông báo "Đã xem" qua WebSocket: /app/chat/read
    @MessageMapping("/chat/read")
    public void notifyReadStatus(@Payload ChatMessageDTO readReceipt) {
        // 1. Cập nhật DB trước
        chatRoomService.markAsRead(readReceipt.getMaNguoiGui(), readReceipt.getMaNguoiNhan());

        // 2. Gửi thông báo đến NGƯỜI GỬI (để họ thấy chữ "Đã xem")
        // Kênh nghe của người gửi: /user/{IdNguoi2}/queue/read-receipt
        messagingTemplate.convertAndSendToUser(
                readReceipt.getMaNguoiNhan(), // Gửi tới người nhắn cho mình
                "/queue/read-receipt",
                readReceipt.getMaPhongChat()); // Trả về mã phòng đã xem
        System.out.println("==> Đã xác nhận xem tại phòng: " + readReceipt.getMaPhongChat());

    }

}