package com.hotel.sicig.dto;

import lombok.Data;

@Data
public class CheckoutRequestDto {
    private Long habitacionId;
    private String metodoPago; 
    private Double montoRecibido;
}
