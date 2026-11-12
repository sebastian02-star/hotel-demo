package com.hotel.sicig.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "clientes")
@Data 
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, length = 50)
    private String nacionalidad = "Perú";

    @Column(name = "tipo_documento", nullable = false, length = 30)
    private String tipoDocumento;

    @Column(name = "numero_documento", nullable = false, unique = true, length = 20)
    private String numeroDocumento;

    @Column(length = 255)
    private String direccion;

    @Column(nullable = false, length = 100)
    private String correo;

    @Column(nullable = false, length = 30)
    private String telefono;

    @Column(nullable = false)
    private Boolean activo = true;
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    @Column(nullable = false)
    private Boolean extranjero = false;
}
