package com.example.bookingclinic.adminsystem.dto.request;

import lombok.Data;

@Data
public class BrowseClinicActionRequest {
    private String maPhongKham;
    private Boolean isApproved;
    private String lyDoTuChoi;
}
