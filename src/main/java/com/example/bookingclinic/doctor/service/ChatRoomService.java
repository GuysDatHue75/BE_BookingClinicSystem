package com.example.bookingclinic.doctor.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.doctor.dto.Chat.ChatInboxResponseDTO;
import com.example.bookingclinic.doctor.dto.Chat.ChatMessageDTO;
import com.example.bookingclinic.doctor.entity.Chat.Chat;
import com.example.bookingclinic.doctor.entity.Chat.ChatRoom;
import com.example.bookingclinic.doctor.repository.AccountRepository;
import com.example.bookingclinic.doctor.repository.Chat.ChatRepository;
import com.example.bookingclinic.doctor.repository.Chat.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

        private final ChatRoomRepository chatRoomRepository;
        private final ChatRepository chatRepository;
        private final AccountRepository accountRepository;

        // 1. Get Chat Room ID (Auto-create if it doesn't exist)
        public String getOrAddChatRoom(String sender, String recipient) {
                return chatRoomRepository
                                .findByMaNguoi1AndMaNguoi2OrMaNguoi1AndMaNguoi2(sender, recipient, sender, recipient)
                                .map(ChatRoom::getMaPhongChat)
                                .orElseGet(() -> {
                                        // Room ID structure: Concatenate 2 IDs in alphabetical order to ensure
                                        // uniqueness
                                        String newRoomId = (sender.compareTo(recipient) < 0)
                                                        ? sender + "_" + recipient
                                                        : recipient + "_" + sender;

                                        ChatRoom newChatRoom = new ChatRoom(newRoomId, sender, recipient,
                                                        LocalDateTime.now());
                                        chatRoomRepository.save(newChatRoom);
                                        return newRoomId;
                                });
        }

        // 2. Save message to Database
        public ChatMessageDTO saveMessage(ChatMessageDTO dto) {
                // 1. Lấy hoặc tạo phòng chat, sau đó gán roomId
                String roomId = getOrAddChatRoom(dto.getMaNguoiGui(), dto.getMaNguoiNhan());

                // 2. MAPPING 1: Chuyển DTO thành Entity để lưu xuống DB
                Chat chatEntity = new Chat();
                chatEntity.setMaPhongChat(roomId);
                chatEntity.setMaNguoiGui(dto.getMaNguoiGui());
                chatEntity.setMaNguoiNhan(dto.getMaNguoiNhan());
                // Nếu client không gửi loại tin nhắn, mặc định là TEXT
                chatEntity.setLoaiTinNhan(dto.getLoaiTinNhan() != null ? dto.getLoaiTinNhan() : "TEXT");
                chatEntity.setNoiDung(dto.getNoiDung());
                chatEntity.setDaXem(false); // Tin nhắn mới gửi thì luôn là chưa xem

                // 3. Cập nhật thời gian phòng chat
                ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow();
                chatRoom.setThoiGianCapNhat(LocalDateTime.now());
                chatRoomRepository.save(chatRoom);

                // 4. LƯU ENTITY XUỐNG DB
                Chat savedEntity = chatRepository.save(chatEntity);

                // 5. MAPPING 2: Chuyển Entity vừa lưu (đã có maTinNhan và thoiGianGui) thành
                // DTO để trả ra Controller
                return ChatMessageDTO.builder()
                                .maTinNhan(savedEntity.getMaTinNhan())
                                .maPhongChat(savedEntity.getMaPhongChat())
                                .maNguoiGui(savedEntity.getMaNguoiGui())
                                .maNguoiNhan(savedEntity.getMaNguoiNhan())
                                .loaiTinNhan(savedEntity.getLoaiTinNhan())
                                .noiDung(savedEntity.getNoiDung())
                                .thoiGianGui(savedEntity.getThoiGianGui())
                                .daXem(savedEntity.getDaXem())
                                .build();
        }

        // 3. Get chat history
        public List<Chat> getChatHistory(String sender, String recipient) {

                String roomId = getOrAddChatRoom(sender, recipient);
                return chatRepository.findByMaPhongChatOrderByThoiGianGuiAsc(roomId);
        }

        // 3.1 phân trang ( cuôn scoll)
        public Page<ChatMessageDTO> getChatHistoryPaginated(String sender, String recipient, int page, int size) {
                // 1 lấy ID
                String roomId = getOrAddChatRoom(sender, recipient);
                // 2. Tạo phân trang
                Pageable pageable = PageRequest.of(page, size);
                // 3. Lấy dât
                Page<Chat> chatPage = chatRepository.findByMaPhongChatOrderByThoiGianGuiDesc(roomId, pageable);
                // 4. Map từ Entity chat -> DTO
                return chatPage.map(chat -> ChatMessageDTO.builder()
                                .maTinNhan(chat.getMaTinNhan())
                                .maPhongChat(chat.getMaPhongChat())
                                .maNguoiGui(chat.getMaNguoiGui())
                                .maNguoiNhan(chat.getMaNguoiNhan())
                                .loaiTinNhan(chat.getLoaiTinNhan())
                                .noiDung(chat.getNoiDung())
                                .thoiGianGui(chat.getThoiGianGui())
                                .daXem(chat.getDaXem())
                                .build());

        }

        // 4. Get List các chat room
        public Page<ChatInboxResponseDTO> getInboxList(String userId, int page, int size) {
                Pageable pageable = PageRequest.of(page, size);

                // 1. Lấy danh sách các phòng chat của mình (Phân trang 10 người)
                Page<ChatRoom> rooms = chatRoomRepository.findByMaNguoi1OrMaNguoi2OrderByThoiGianCapNhatDesc(userId,
                                userId,
                                pageable);

                // 2. Chuyển đổi từ ChatRoom sang ChatInboxResponseDTO
                return rooms.map(room -> {
                        // Xác định ai là đối phương (người không phải là mình)
                        String maDoiPhuong = room.getMaNguoi1().equals(userId) ? room.getMaNguoi2()
                                        : room.getMaNguoi1();

                        // Lấy tên đối phương từ bảng Account
                        String tenDoiPhuong = accountRepository.findById(maDoiPhuong)
                                        .map(acc -> acc.getHoVaTen()).orElse("Người dùng hệ thống");

                        // Lấy tin nhắn cuối cùng trong phòng
                        Chat lastChat = chatRepository
                                        .findFirstByMaPhongChatOrderByThoiGianGuiDesc(room.getMaPhongChat())
                                        .orElse(null);

                        // Đếm số tin nhắn chưa đọc (mình là người nhận và daXem = false)
                        long unreadCount = chatRepository.countByMaPhongChatAndMaNguoiNhanAndDaXem(
                                        room.getMaPhongChat(), userId,
                                        false);

                        return ChatInboxResponseDTO.builder()
                                        .maPhongChat(room.getMaPhongChat())
                                        .maDoiPhuong(maDoiPhuong)
                                        .tenDoiPhuong(tenDoiPhuong)
                                        .tinNhanCuoi(lastChat != null ? lastChat.getNoiDung() : "")
                                        .thoiGianCuoi(room.getThoiGianCapNhat())
                                        .soTinChuaDoc(unreadCount)
                                        .maNguoiGuiCuoi(lastChat != null ? lastChat.getMaNguoiGui() : "")
                                        .daXemCuoi(lastChat != null ? lastChat.getDaXem() : false)

                                        .build();

                });

        }

        // 5. Cập nhật daxem
        public void markAsRead(String sender, String recipient) {
                // 1. Lấy Room ID
                String roomId = getOrAddChatRoom(sender, recipient);

                // 2. Update trạng thái trong DB
                // Ở đây userId truyền vào là 'sender' (người đang mở khung chat)
                chatRepository.markMessagesAsRead(roomId, sender);
        }
}