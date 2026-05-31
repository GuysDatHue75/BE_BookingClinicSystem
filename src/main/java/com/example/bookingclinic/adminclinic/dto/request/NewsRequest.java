package com.example.bookingclinic.adminclinic.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsRequest {
    private String tieuDe;
    private String moTaNgan;
    private String noiDung;
}
