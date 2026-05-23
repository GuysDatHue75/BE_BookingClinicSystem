package com.example.bookingclinic.user.dto;

import lombok.Data;

@Data
public class ChatResponseDTO {
    private String answer;
    private String kieuTraVe; 
    private String entity;    
    private Object data;    
    public ChatResponseDTO(String answer, String kieuTraVe, String entity, Object data) {
        this.answer = answer;
        this.kieuTraVe = kieuTraVe;
        this.entity = entity;
        this.data = data;
    }
}
