package com.hotel.sicig.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "habitaciones")
@Data
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de habitación es obligatorio")
    @Column(name = "numero_habitacion", unique = true, nullable = false, length = 10)
    private String numeroHabitacion; 

    @NotNull(message = "El tipo de habitación es obligatorio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_id", nullable = false)
    private TipoHabitacion tipoHabitacion; 

    @NotNull(message = "El piso es obligatorio")
    @Min(value = 1, message = "El piso debe ser al menos 1")
    @Column(nullable = false)
    private Integer piso;

    @NotNull(message = "El precio por noche es obligatorio")
    @DecimalMin(value = "0.01", inclusive = true, message = "El precio por noche debe ser mayor a cero y no puede ser negativo")
    @Column(name = "precio_noche", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioNoche;

    @Column(nullable = false, length = 30)
    private String estado = "DISPONIBLE"; 

    @Column(length = 255)
    private String descripcion;
}