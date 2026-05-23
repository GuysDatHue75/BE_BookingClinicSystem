package com.example.bookingclinic.adminsystem.entity;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tinh_nang_goi_dang_ky")
public class SubscriptionPackageFeaturesEntity {
    @EmbeddedId
    private SubscriptionPackageFeaturesId id;

    @ManyToOne
    @MapsId("maGoi")
    @JoinColumn(name = "ma_goi", nullable = false)
    private SubscriptionPackageEntity subscriptionPackage;

    @ManyToOne
    @MapsId("maTinhNang")
    @JoinColumn(name = "ma_tinh_nang", nullable = false)
    private FeaturesEntity features;
}
