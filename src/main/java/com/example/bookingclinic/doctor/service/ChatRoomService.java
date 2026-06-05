package com.example.bookingclinic.doctor.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookingclinic.doctor.dto.Chat.ChatInboxResponseDTO;
import com.example.bookingclinic.doctor.dto.Chat.ChatMessageDTO;
import com.example.bookingclinic.doctor.entity.Account;
import com.example.bookingclinic.doctor.entity.Chat.Chat;
import com.example.bookingclinic.doctor.entity.Chat.ChatRoom;
import com.example.bookingclinic.doctor.repository.AccountRepository;
import com.example.bookingclinic.doctor.repository.Chat.ChatRepository;
import com.example.bookingclinic.doctor.repository.Chat.ChatRoomRepository;
import com.example.bookingclinic.doctor.service.Prescription.FileStorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {

        private final ChatRoomRepository chatRoomRepository;
        private final ChatRepository chatRepository;
        private final AccountRepository accountRepository;
        private final FileStorageService fileStorageService;

        // 1. Lấy hoặc Tự động tạo phòng chat dựa trên định danh chuẩn hóa mã hóa
        public String getOrAddChatRoom(String sender, String recipient) {
                if (sender == null || recipient == null) {
                        throw new IllegalArgumentException("Mã người gửi hoặc người nhận không được để trống!");
                }

                // Tạo chuỗi Room ID duy nhất theo thứ tự bảng chữ cái để không lo hoán đổi vị
                // trí
                String roomId = (sender.compareTo(recipient) < 0)
                                ? sender + "_" + recipient
                                : recipient + "_" + sender;

                return chatRoomRepository.findById(roomId)
                                .map(ChatRoom::getMaPhongChat)
                                .orElseGet(() -> {
                                        ChatRoom newChatRoom = new ChatRoom();
                                        newChatRoom.setMaPhongChat(roomId);
                                        newChatRoom.setMaNguoi1(sender);
                                        newChatRoom.setMaNguoi2(recipient);
                                        newChatRoom.setThoiGianCapNhat(LocalDateTime.now());
                                        chatRoomRepository.save(newChatRoom);
                                        return roomId;
                                });
        }

        // 2. Lưu tin nhắn xuống Database an toàn, không lo sập luồng xử lý
        public ChatMessageDTO saveMessage(ChatMessageDTO dto) {
                // Lấy hoặc khởi tạo phòng chat
                String roomId = getOrAddChatRoom(dto.getMaNguoiGui(), dto.getMaNguoiNhan());

                // Mapping dữ liệu từ DTO sang Entity để ghi nhận vào DB
                Chat chatEntity = new Chat();
                chatEntity.setMaPhongChat(roomId);
                chatEntity.setMaNguoiGui(dto.getMaNguoiGui());
                chatEntity.setMaNguoiNhan(dto.getMaNguoiNhan());
                chatEntity.setLoaiTinNhan(dto.getLoaiTinNhan() != null ? dto.getLoaiTinNhan() : "TEXT");
                chatEntity.setNoiDung(dto.getNoiDung());
                chatEntity.setDaXem(false);
                chatEntity.setThoiGianGui(LocalDateTime.now());

                // Thực hiện lưu tin nhắn
                Chat savedEntity = chatRepository.save(chatEntity);

                // Cập nhật lại thời gian tương tác cuối cùng của phòng chat một cách an toàn
                chatRoomRepository.findById(roomId).ifPresent(chatRoom -> {
                        chatRoom.setThoiGianCapNhat(LocalDateTime.now());
                        chatRoomRepository.save(chatRoom);
                });
                String avatarNguoiGui = accountRepository.findById(savedEntity.getMaNguoiGui())
                                .map(Account::getAnhDaiDien).orElse(null);
                // Trả dữ liệu DTO chuẩn hóa ra ngoài Controller phát tín hiệu Realtime
                return ChatMessageDTO.builder()
                                .maTinNhan(savedEntity.getMaTinNhan())
                                .maPhongChat(savedEntity.getMaPhongChat())
                                .maNguoiGui(savedEntity.getMaNguoiGui())
                                .maNguoiNhan(savedEntity.getMaNguoiNhan())
                                .loaiTinNhan(savedEntity.getLoaiTinNhan())
                                .noiDung(savedEntity.getNoiDung())
                                .thoiGianGui(savedEntity.getThoiGianGui())
                                .avatarNguoiGui(avatarNguoiGui)
                                .daXem(savedEntity.getDaXem())
                                .build();
        }

        // 3. Lấy toàn bộ lịch sử hội thoại
        @Transactional(readOnly = true)
        public List<Chat> getChatHistory(String sender, String recipient) {
                String roomId = getOrAddChatRoom(sender, recipient);
                return chatRepository.findByMaPhongChatOrderByThoiGianGuiAsc(roomId);
        }

        // 3.1 Phân trang lịch sử chat (Hỗ trợ cuộn trang mượt mà)
        @Transactional(readOnly = true)
        public Page<ChatMessageDTO> getChatHistoryPaginated(String sender, String recipient, int page, int size) {
                String roomId = getOrAddChatRoom(sender, recipient);
                Pageable pageable = PageRequest.of(page, size);
                Page<Chat> chatPage = chatRepository.findByMaPhongChatOrderByThoiGianGuiDesc(roomId, pageable);

                String avatarSender = accountRepository.findById(sender).map(Account::getAnhDaiDien).orElse(null);
                String avatarRecipient = accountRepository.findById(recipient).map(Account::getAnhDaiDien).orElse(null);

                return chatPage.map(chat -> {
                        String currentAvatar = chat.getMaNguoiGui().equals(sender) ? avatarSender : avatarRecipient;
                        return ChatMessageDTO.builder()
                                        .maTinNhan(chat.getMaTinNhan())
                                        .maPhongChat(chat.getMaPhongChat())
                                        .maNguoiGui(chat.getMaNguoiGui())
                                        .maNguoiNhan(chat.getMaNguoiNhan())
                                        .loaiTinNhan(chat.getLoaiTinNhan())
                                        .noiDung(chat.getNoiDung())
                                        .thoiGianGui(chat.getThoiGianGui())
                                        .avatarNguoiGui(currentAvatar)
                                        .daXem(chat.getDaXem())
                                        .build();
                });

        }

        // 4. Lấy danh sách các phòng chat hiện có (Hộp thư thoại)
        @Transactional(readOnly = true)
        public Page<ChatInboxResponseDTO> getInboxList(String userId, int page, int size) {
                Pageable pageable = PageRequest.of(page, size);
                Page<ChatRoom> rooms = chatRoomRepository.findByMaNguoi1OrMaNguoi2OrderByThoiGianCapNhatDesc(userId,
                                userId, pageable);

                return rooms.map(room -> {
                        // Xác định ID của đối phương đang nhắn với mình
                        String maDoiPhuong = room.getMaNguoi1().equals(userId) ? room.getMaNguoi2()
                                        : room.getMaNguoi1();
                        var accountOpt = accountRepository.findById(maDoiPhuong);

                        // Truy vấn thông tin tên hiển thị đối phương công khai
                        String tenDoiPhuong = accountRepository.findById(maDoiPhuong)
                                        .map(acc -> acc.getHoVaTen()).orElse("Người dùng hệ thống");
                        String avatarDoiPhuong = accountOpt.map(Account::getAnhDaiDien).orElse(null);
                        // Lấy nội dung tin nhắn cuối cùng để hiển thị đoạn trích (Snippet)
                        Chat lastChat = chatRepository
                                        .findFirstByMaPhongChatOrderByThoiGianGuiDesc(room.getMaPhongChat())
                                        .orElse(null);

                        // Đếm tổng số tin nhắn chưa đọc từ đối phương gửi tới
                        long unreadCount = chatRepository.countByMaPhongChatAndMaNguoiNhanAndDaXem(
                                        room.getMaPhongChat(), userId, false);

                        return ChatInboxResponseDTO.builder()
                                        .maPhongChat(room.getMaPhongChat())
                                        .maDoiPhuong(maDoiPhuong)
                                        .tenDoiPhuong(tenDoiPhuong)
                                        .avatarDoiPhuong(avatarDoiPhuong)
                                        .tinNhanCuoi(lastChat != null ? lastChat.getNoiDung() : "")
                                        .thoiGianCuoi(room.getThoiGianCapNhat())
                                        .soTinChuaDoc(unreadCount)
                                        .maNguoiGuiCuoi(lastChat != null ? lastChat.getMaNguoiGui() : "")
                                        .daXemCuoi(lastChat != null ? lastChat.getDaXem() : false)
                                        .build();
                });
        }

        // 5. Đánh dấu trạng thái đã xem toàn bộ tin nhắn trong phòng
        public void markAsRead(String sender, String recipient) {
                String roomId = getOrAddChatRoom(sender, recipient);
                chatRepository.markMessagesAsRead(roomId, recipient);
        }

        public String uploadChatAttachment(MultipartFile file) {
                if (file == null || file.isEmpty()) {
                        throw new IllegalArgumentException("File gửi lên không được để trống!");
                }
                try {
                        // Tái sử dụng FileStorageService của hệ thống để lưu file vào thư mục uploads
                        String fileUrl = fileStorageService.storeFile(file);

                        // Trả về đường dẫn URL của file (Ví dụ: /uploads/abc.jpg)
                        return fileUrl;
                } catch (Exception e) {
                        throw new RuntimeException("Lỗi trong quá trình lưu trữ file chat: " + e.getMessage());
                }
        }
}