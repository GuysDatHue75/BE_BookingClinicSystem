package com.example.bookingclinic.adminsystem.dto.request;

import lombok.Data;

@Data
public class BrowseDoctorActionRequest {
    private String maBacSi;
    private Boolean isApproved;
    private String lyDoTuChoi;
    
}
