package com.example.bookingclinic.doctor.controller.Chat;

import com.example.bookingclinic.doctor.dto.Chat.ChatInboxResponseDTO;
import com.example.bookingclinic.doctor.dto.Chat.ChatMessageDTO;
import com.example.bookingclinic.doctor.service.ChatRoomService;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
@CrossOrigin("*") // origins = "http://localhost:3000"
@RequiredArgsConstructor // Tự động tạo Constructor
public class ChatRestController {

    private final ChatRoomService chatRoomService;
    private final SimpMessageSendingOperations messagingTemplate;

    // Fetch chat history API: GET /api/v1/chat/history/TK01/TK02?page=0&size=20
    @GetMapping("/history/{maNguoi1}/{maNguoi2}")
    public ResponseEntity<Page<ChatMessageDTO>> getChatHistory(
            @PathVariable String maNguoi1,
            @PathVariable String maNguoi2,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<ChatMessageDTO> history = chatRoomService.getChatHistoryPaginated(maNguoi1, maNguoi2, page, size);
        return ResponseEntity.ok(history);
    }

    // API: Lấy danh sách Inbox (Hộp thư đến) bên trái
    @GetMapping("/inbox/{userId}")
    public ResponseEntity<Page<ChatInboxResponseDTO>> getInbox(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(chatRoomService.getInboxList(userId, page, size));
    }

    // API: POST /api/v1/chat/read/TK01/TK02 (Dành cho HTTP REST thường)
    @PostMapping("/read/{IdNguoi1}/{IdNguoi2}")
    public ResponseEntity<?> markAsRead(
            @PathVariable String IdNguoi1,
            @PathVariable String IdNguoi2) {
        chatRoomService.markAsRead(IdNguoi1, IdNguoi2);
        return ResponseEntity.ok().build();
    }

    // 1. Người dùng gửi tín hiệu đang gõ đến: /app/chat/typing
    @MessageMapping("/chat/typing")
    public void handleTyping(@Payload Map<String, Object> payload) {
        String maNguoiGui = (String) payload.get("maNguoiGui");
        String maNguoiNhan = (String) payload.get("maNguoiNhan");
        Boolean isTyping = (Boolean) payload.get("isTyping");

        Map<String, Object> response = new HashMap<>();
        response.put("maNguoiGui", maNguoiGui);
        response.put("isTyping", isTyping);

        // ĐÃ SỬA: Đổi sang convertAndSend (bắn thẳng) và nối chuỗi cho khớp với React
        messagingTemplate.convertAndSend("/topic/typing/" + maNguoiNhan, response);
    }

    // 2. BỔ SUNG THÊM: Hứng tín hiệu "Đã xem" từ React: /app/chat/read
    @MessageMapping("/chat/read")
    public void handleRead(@Payload Map<String, Object> payload) {
        // Lấy thông tin từ payload (khớp với cục JSON React gửi lên)
        String maNguoiGui = (String) payload.get("maNguoiGui"); // Người gửi tin nhắn ban đầu
        String maNguoiNhan = (String) payload.get("maNguoiNhan"); // Người vừa bấm vào đọc

        // Lưu xuống DB đánh dấu là đã đọc (tái sử dụng hàm của service)
        chatRoomService.markAsRead(maNguoiGui, maNguoiNhan);

        // Bắn tín hiệu "đã đọc" qua WebSocket trả về cho người gửi ban đầu
        messagingTemplate.convertAndSend("/topic/read/" + maNguoiGui, payload);
    }
}