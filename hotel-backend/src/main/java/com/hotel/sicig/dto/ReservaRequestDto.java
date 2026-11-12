
package com.hotel.sicig.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReservaRequestDto {
    private Long clienteId;
    private Long habitacionId;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private String canalOrigen;
}
