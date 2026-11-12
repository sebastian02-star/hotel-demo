package com.hotel.sicig.dto;

import lombok.Data;

@Data
public class ClienteRequestDto {
    private String nombreCompleto;
    private String documentoIdentidad; 
    private String telefono;
    private String email;
}
