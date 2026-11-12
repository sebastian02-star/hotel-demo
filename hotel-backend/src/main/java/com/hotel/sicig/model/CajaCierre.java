
package com.hotel.sicig.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "caja_cierres")
@Data
public class CajaCierre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "monto_inicial", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoInicial;

    @Column(name = "monto_final_teorico", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoFinalTeorico;

    @Column(name = "monto_final_real", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoFinalReal;

    @Column(name = "fecha_apertura", nullable = false)
    private LocalDateTime fechaApertura = LocalDateTime.now();

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Column(nullable = false, length = 30)
    private String estado = "ABIERTA"; 
}
