package com.hotel.sicig.dto;

import lombok.Data;
import java.util.List;

@Data
public class ComandaRequestDto {
    private Long habitacionId; 
    private List<ItemComandaDto> items;
    
    @Data
    public static class ItemComandaDto {
        private Long productoId;
        private Integer cantidad;
    }
}
