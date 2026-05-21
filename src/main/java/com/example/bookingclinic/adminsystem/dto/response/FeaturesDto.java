package com.example.bookingclinic.adminsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeaturesDto {
    private String maTinhNang;
    private String tenTinhNang;
}
