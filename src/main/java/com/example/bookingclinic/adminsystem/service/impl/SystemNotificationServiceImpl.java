package com.example.bookingclinic.adminsystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookingclinic.adminsystem.dto.request.NotificationRequest;
import com.example.bookingclinic.adminsystem.dto.request.NotificationSearchRequest;
import com.example.bookingclinic.adminsystem.dto.response.NotificationResponse;
import com.example.bookingclinic.adminclinic.entity.AccountEntity;
import com.example.bookingclinic.adminclinic.entity.NotificationAccountEntity;
import com.example.bookingclinic.adminclinic.entity.NotificationAccountId;
import com.example.bookingclinic.adminclinic.entity.NotificationEntity;
import com.example.bookingclinic.adminsystem.repository.SystemAccountRepository;
import com.example.bookingclinic.adminsystem.repository.SystemNotificationAccountRepository;
import com.example.bookingclinic.adminsystem.repository.SystemNotificationRepository;
import com.example.bookingclinic.adminsystem.repository.projection.NotificationProjection;
import com.example.bookingclinic.adminsystem.service.SystemNotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SystemNotificationServiceImpl implements SystemNotificationService {

    private final SystemNotificationRepository notificationRepository;
    private final SystemNotificationAccountRepository notificationAccountRepository;
    private final SystemAccountRepository accountRepository;
    private final SimpMessagingTemplate messagingTemplate;

    private void saveNotificationAccount(NotificationEntity notification, String userId){
        AccountEntity account = accountRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
        NotificationAccountEntity na = new NotificationAccountEntity();
        na.setId(new NotificationAccountId(notification.getMaThongBao(), userId));
        na.setNotification(notification);
        na.setAccount(account);
        na.setIsRead(false);
        notificationAccountRepository.save(na);

        messagingTemplate.convertAndSend(
            "/topic/notifications/" + userId,
            "Bạn có một thông báo mới " 
        );
    }

    @Override
    public Page<NotificationResponse> search(NotificationSearchRequest request) {
        return notificationRepository.search(request);
    }

    private String generateMaThongBao(){
        List<String> danhSachMaThongBao = notificationRepository.findAllMaThongBao();
        int maxNumber = 0;
        for(String ma : danhSachMaThongBao) {
            if(ma != null && ma.startsWith("TB")) {
                try {
                    int currentNumber = Integer.parseInt(ma.substring(2));
                    if(currentNumber > maxNumber) {
                        maxNumber = currentNumber;
                    }
                } catch(NumberFormatException e) {
                    // Ignore non-numeric suffix
                }
            }
        }
        int nextNumber = maxNumber + 1;
        return String.format("TB%02d", nextNumber);
    }
    
    @Override
    public void createNotification(NotificationRequest request) {
        String maThongBao = generateMaThongBao();
        AccountEntity accountProxy = AccountEntity.builder().maTaiKhoan(request.getMaTaiKhoan()).build();
        NotificationEntity notification = NotificationEntity.builder()
            .maThongBao(maThongBao)
            .account(accountProxy)
            .tieuDe(request.getTieuDe())
            .noiDung(request.getNoiDung())
            .loaiThongBao(request.getLoaiThongBao())
            .doiTuongNhan(request.getDoiTuongNhan())
            .thoiGianGui(LocalDateTime.now())
            .isDeleted(false)
            .build();

        notificationRepository.save(notification);

        if(request.getDanhSachNguoiNhan() != null && !request.getDanhSachNguoiNhan().isEmpty()){
            for(String userId : request.getDanhSachNguoiNhan()){
                saveNotificationAccount(notification, userId);
            }
        } else if(request.getDoiTuongNhan() != null && !request.getDoiTuongNhan().isEmpty()){
            List<AccountEntity> users;
            if(request.getDoiTuongNhan().equalsIgnoreCase("Tất cả người dùng") || request.getDoiTuongNhan().equalsIgnoreCase("all")) { 
                users = accountRepository.findAll();
            } else {
                users = accountRepository.findByVaiTroIgnoreCase(request.getDoiTuongNhan());
            }
            for(AccountEntity user : users){
                saveNotificationAccount(notification, user.getMaTaiKhoan());
            }
        }
    }

    @Override
    public void updateNotification(NotificationRequest request) {
        NotificationEntity oldNotification = notificationRepository.findById(request.getMaThongBao())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo"));
        
        if (Boolean.TRUE.equals(oldNotification.getIsDeleted())) {
            throw new RuntimeException("Thông báo này đã bị xóa và không thể cập nhật");
        }

        boolean isContentChanged = !oldNotification.getTieuDe().equals(request.getTieuDe()) 
                                || !oldNotification.getNoiDung().equals(request.getNoiDung());

        java.util.Set<String> newUserIds = new java.util.HashSet<>();
        if (request.getDanhSachNguoiNhan() != null && !request.getDanhSachNguoiNhan().isEmpty()) {
            newUserIds.addAll(request.getDanhSachNguoiNhan());
        } else if (request.getDoiTuongNhan() != null && !request.getDoiTuongNhan().isEmpty()) {
            List<AccountEntity> users;
            if (request.getDoiTuongNhan().equalsIgnoreCase("Tất cả người dùng") || request.getDoiTuongNhan().equalsIgnoreCase("all")) {
                users = accountRepository.findAll(); // Lấy toàn bộ
            } else {
                users = accountRepository.findByVaiTroIgnoreCase(request.getDoiTuongNhan());
            }
            newUserIds.addAll(users.stream().map(AccountEntity::getMaTaiKhoan).collect(java.util.stream.Collectors.toSet()));
        }

        if (isContentChanged) {

            oldNotification.setIsDeleted(true);
            notificationRepository.save(oldNotification);

            String prefix = "[Cập nhật] ";
            String newTitle = request.getTieuDe().startsWith(prefix) ? request.getTieuDe() : prefix + request.getTieuDe();
            AccountEntity accountProxy = AccountEntity.builder().maTaiKhoan(request.getMaTaiKhoan()).build();

            NotificationEntity newNotification = NotificationEntity.builder()
                .maThongBao(generateMaThongBao())
                .account(accountProxy)
                .tieuDe(newTitle)
                .noiDung(request.getNoiDung())
                .loaiThongBao(request.getLoaiThongBao())
                .doiTuongNhan(request.getDoiTuongNhan())
                .thoiGianGui(LocalDateTime.now())
                .isDeleted(false)
                .build();
            
            notificationRepository.save(newNotification);

            for (String userId : newUserIds) {
                saveNotificationAccount(newNotification, userId);
            }

        } else {
            
            oldNotification.setLoaiThongBao(request.getLoaiThongBao());
            oldNotification.setDoiTuongNhan(request.getDoiTuongNhan());
            notificationRepository.save(oldNotification);

            List<NotificationAccountEntity> existingMappings = notificationAccountRepository.findByIdMaThongBao(oldNotification.getMaThongBao());
            
            java.util.Set<String> oldUserIds = existingMappings.stream()
                .map(na -> na.getId().getMaTaiKhoan())
                .collect(java.util.stream.Collectors.toSet());

            for (NotificationAccountEntity existing : existingMappings) {
                if (!newUserIds.contains(existing.getId().getMaTaiKhoan())) {
                    notificationAccountRepository.delete(existing);
                }
            }

            for (String newUserId : newUserIds) {
                if (!oldUserIds.contains(newUserId)) {
                    saveNotificationAccount(oldNotification, newUserId);
                }
            }
        }
    }

    @Override
    public void deleteNotification(String maThongBao) {
        NotificationEntity notification = notificationRepository.findById(maThongBao)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo"));
        
        notification.setIsDeleted(true);
        notificationRepository.save(notification);
    }

    // @Override
    // public void markAsRead(String maThongBao, String maTaiKhoan) {
    //     notificationAccountRepository.markAsRead(maThongBao, maTaiKhoan);
    // }

    // @Override
    // public long countUnread(String maTaiKhoan) {
    //     return notificationAccountRepository.countByIdMaTaiKhoanAndIsReadFalse(maTaiKhoan);
    // }

    @Override
    public NotificationProjection getDetail(String maThongBao) {
        return notificationRepository.getDetail(maThongBao);
    }
}
