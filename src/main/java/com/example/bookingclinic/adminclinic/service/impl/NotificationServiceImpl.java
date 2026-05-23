package com.example.bookingclinic.adminclinic.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookingclinic.adminclinic.dto.request.NotificationRequest;
import com.example.bookingclinic.adminclinic.dto.request.NotificationSearchRequest;
import com.example.bookingclinic.adminclinic.dto.response.NotificationResponse;
import com.example.bookingclinic.adminclinic.entity.AccountEntity;
import com.example.bookingclinic.adminclinic.entity.ClinicEntity;
import com.example.bookingclinic.adminclinic.entity.NotificationAccountEntity;
import com.example.bookingclinic.adminclinic.entity.NotificationAccountId;
import com.example.bookingclinic.adminclinic.entity.NotificationEntity;
import com.example.bookingclinic.adminclinic.repository.ClinicAccountRepository;
import com.example.bookingclinic.adminclinic.repository.ClinicRepository;
import com.example.bookingclinic.adminclinic.repository.NotificationAccountRepository;
import com.example.bookingclinic.adminclinic.repository.NotificationRepository;
import com.example.bookingclinic.adminclinic.repository.projection.NotificationProjection;
import com.example.bookingclinic.adminclinic.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    private final NotificationAccountRepository notificationAccountRepository;
    private final ClinicAccountRepository accountRepository;
    private final ClinicRepository clinicRepository;
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
        String maxMaThongBao = notificationRepository.findMaxMaThongBao();
            if(maxMaThongBao == null || maxMaThongBao.isEmpty()) {
                return "TB01";
            }
                try {
                    return String.format("TB%02d", Integer.parseInt(maxMaThongBao.substring(2)) + 1);
                } catch(NumberFormatException e) {
                    return "TB01";
                }
        }

    private Set<String> getValidUserIdsForClinic(NotificationRequest request){
        ClinicEntity clinic = clinicRepository.findByAccount_MaTaiKhoan(request.getMaTaiKhoan());
        if (clinic == null) {
            throw new RuntimeException("Không tìm thấy tài khoản phòng khám");
        }

        String maPhongKham = clinic.getMaPhongKham();

        Set<String> validUserIds = new HashSet<>();

        if (request.getDanhSachNguoiNhan() != null && !request.getDanhSachNguoiNhan().isEmpty()) {
            for (String userId : request.getDanhSachNguoiNhan()) {
                if (!accountRepository.checkUserBelongsToClinic(userId, maPhongKham)) {
                    throw new RuntimeException("Lỗi bảo mật: " + userId + " không thuộc phòng khám này!");
                }
                validUserIds.add(userId);
            }
        } else if (request.getDoiTuongNhan() != null && !request.getDoiTuongNhan().isEmpty()) {
            String vaiTro = request.getDoiTuongNhan().toUpperCase();
            if (vaiTro.equals("BacSi")) {
                validUserIds.addAll(accountRepository.findByVaiTroIgnoreCaseAndMaPhongKham(vaiTro, maPhongKham).stream().map(AccountEntity::getMaTaiKhoan).toList());
            } else if (vaiTro.equals("BenhNhan")) {
                validUserIds.addAll(accountRepository.findBenhNhanByMaPhongKham(maPhongKham).stream().map(AccountEntity::getMaTaiKhoan).toList());
            } else { throw new RuntimeException("Chỉ được gửi cho BacSi hoặc BENH_NHAN"); }
        }
        return validUserIds;
    }
    
    @Override
    public void createNotification(NotificationRequest request) {
        Set<String> validUserIds = getValidUserIdsForClinic(request);
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
            .files(request.getFiles())
            .anhThongBao(request.getAnhThongBao())
            .build();

        notificationRepository.save(notification);

        for (String userId: validUserIds){
            saveNotificationAccount(notification, userId);
        }
    }

    @Override
    public void updateNotification(NotificationRequest request) {
        NotificationEntity oldNotification = notificationRepository.findById(request.getMaThongBao())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo"));
        
        if (Boolean.TRUE.equals(oldNotification.getIsDeleted())) {
            throw new RuntimeException("Thông báo này đã bị xóa và không thể cập nhật");
        }

        Set<String> newUserIds = getValidUserIdsForClinic(request);

        boolean isContentChanged = !oldNotification.getTieuDe().equals(request.getTieuDe()) 
                                || !oldNotification.getNoiDung().equals(request.getNoiDung());

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
                .files(request.getFiles())
                .anhThongBao(request.getAnhThongBao())
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
            
            Set<String> oldUserIds = existingMappings.stream()
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

    @Override
    public void markAsRead(String maThongBao, String maTaiKhoan) {
        notificationAccountRepository.markAsRead(maThongBao, maTaiKhoan);
    }

    @Override
    public long countUnread(String maTaiKhoan) {
        return notificationAccountRepository.countById_MaTaiKhoanAndIsReadFalse(maTaiKhoan);
    }

    @Override
    public NotificationProjection getDetail(String maThongBao) {
        return notificationRepository.getDetail(maThongBao);
    }
}
