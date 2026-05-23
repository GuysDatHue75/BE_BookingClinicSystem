package com.example.bookingclinic.adminsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationIsReadResponse {
    private String maTaiKhoan;
    private String hoVaTen;
    private Boolean isRead;
}
