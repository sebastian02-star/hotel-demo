

package com.hotel.sicig.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "consumos")
@Data
public class Consumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    @Column(name = "descripcion_item", nullable = false, length = 200)
    private String descripcionItem; 

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "fecha_consumo", nullable = false)
    private LocalDateTime fechaConsumo = LocalDateTime.now();

    @Column(name = "canal_consumo", nullable = false, length = 50)
    private String canalConsumo; // RESTAURANTE, BAR, LAVANDERIA, TOUR
}