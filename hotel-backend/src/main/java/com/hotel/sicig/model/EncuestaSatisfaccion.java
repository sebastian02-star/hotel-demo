

package com.hotel.sicig.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Entity
@Table(name = "encuestas_satisfaccion")
@Data
public class EncuestaSatisfaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "puntuacion_general", nullable = false)
    private Integer puntuacionGeneral; 

    @Column(name = "respuestas_json", columnDefinition = "jsonb")
    private String respuestasJson; 

    @Column(name = "fecha_encuesta", nullable = false)
    private LocalDateTime fechaEncuesta = LocalDateTime.now();
}
