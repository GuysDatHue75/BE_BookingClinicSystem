package com.example.bookingclinic.adminsystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookingclinic.adminsystem.dto.request.NotificationRequest;
import com.example.bookingclinic.adminsystem.dto.request.NotificationSearchRequest;
import com.example.bookingclinic.adminsystem.entity.AccountEntity;
import com.example.bookingclinic.adminsystem.entity.NotificationAccountEntity;
import com.example.bookingclinic.adminsystem.entity.NotificationAccountId;
import com.example.bookingclinic.adminsystem.entity.NotificationEntity;
import com.example.bookingclinic.adminsystem.repository.AccountRepository;
import com.example.bookingclinic.adminsystem.repository.NotificationAccountRepository;
import com.example.bookingclinic.adminsystem.repository.NotificationRepository;
import com.example.bookingclinic.adminsystem.repository.projection.NotificationProjection;
import com.example.bookingclinic.adminsystem.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationAccountRepository notificationAccountRepository;
    private final AccountRepository accountRepository;
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
    public Page<NotificationProjection> search(NotificationSearchRequest request) {
        return notificationRepository.search(request);
    }

    private String generateMaThongBao(){
        return "TB"+ UUID.randomUUID().toString().substring(0,8);
    }
    
    @Override
    public void createNotification(NotificationRequest request) {
        String maThongBao = generateMaThongBao();
        NotificationEntity notification = NotificationEntity.builder()
            .maThongBao(maThongBao)
            .maTaiKhoan(request.getMaTaiKhoan())
            .tieuDe(request.getTieuDe())
            .noiDung(request.getNoiDung())
            .loaiThongBao(request.getLoaiThongBao())
            .doiTuongNhan(request.getDoituongNhan())
            .thoiGianGui(LocalDateTime.now())
            .isDeleted(false)
            .build();

        notificationRepository.save(notification);

        if(request.getDanhSachNguoiNhan() != null && !request.getDanhSachNguoiNhan().isEmpty()){
            for(String userId : request.getDanhSachNguoiNhan()){
                saveNotificationAccount(notification, userId);
            }
        } else if(request.getDoituongNhan() != null){
            List<AccountEntity> users = accountRepository.findAll()
                .stream()
                .filter(x -> x.getVaiTro().equalsIgnoreCase(request.getDoituongNhan()))
                .toList();
            for(AccountEntity user : users){
                saveNotificationAccount(notification, user.getMaTaiKhoan());
            }
        }
    }

    @Override
    public void updateNotification(NotificationRequest request) {
        NotificationEntity notification = notificationRepository.findById(request.getMaThongBao())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo"));
        
        notification.setTieuDe(request.getTieuDe());
        notification.setNoiDung(request.getNoiDung());
        notification.setLoaiThongBao(request.getLoaiThongBao());
        notification.setDoiTuongNhan(request.getDoituongNhan());
        notification.setThoiGianGui(LocalDateTime.now());
        notification.setIsDeleted(false);
        
        notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(String maThongBao) {
        NotificationEntity notification = notificationRepository.findById(maThongBao)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo"));
        
        notification.setIsDeleted(true);
        notificationRepository.save(notification);
    }

    @Override
    public void markAsRead(String maThongBao, String maTaiKhoan) {
        notificationAccountRepository.markAsRead(maThongBao, maTaiKhoan);
    }

    @Override
    public long countUnread(String maTaiKhoan) {
        return notificationAccountRepository.countByIdMaTaiKhoanAndIsReadFalse(maTaiKhoan);
    }

    @Override
    public NotificationProjection getDetail(String maThongBao, String maTaiKhoan) {
        NotificationProjection result = notificationRepository.getDetail(maThongBao, maTaiKhoan);

        List<String> receivers = notificationAccountRepository.findAll()
            .stream()
            .filter(x -> x.getId().getMaThongBao().equals(maThongBao))
            .map(x -> x.getId().getMaTaiKhoan())
            .toList();
        
        if(result != null){
            result.setDanhSachNguoiNhan(receivers);
        }

        return result;
    }
}
