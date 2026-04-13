package com.example.bookingclinic.adminsystem.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPackageFeaturesId implements Serializable {
    private String maGoi;
    private String maTinhNang;
    
}
