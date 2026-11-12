package com.hotel.sicig.controller;

import com.hotel.sicig.model.TipoHabitacion;
import com.hotel.sicig.repository.TipoHabitacionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-habitacion")
public class TipoHabitacionController {

    private final TipoHabitacionRepository tipoHabitacionRepository;

    TipoHabitacionController(TipoHabitacionRepository tipoHabitacionRepository) {
        this.tipoHabitacionRepository = tipoHabitacionRepository;
    }

    @GetMapping
    public List<TipoHabitacion> listarTipos() {
        return tipoHabitacionRepository.findByActivoTrue(); 
    }

    @PostMapping
    public ResponseEntity<TipoHabitacion> crearTipoHabitacion(@RequestBody TipoHabitacion tipoHabitacion) {
        tipoHabitacion.setActivo(true);
        TipoHabitacion nuevoTipo = tipoHabitacionRepository.save(tipoHabitacion);
        return ResponseEntity.ok(nuevoTipo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoHabitacion> actualizarTipoHabitacion(@PathVariable Long id, @RequestBody TipoHabitacion detalles) {
        return tipoHabitacionRepository.findById(id).map(tipo -> {
            tipo.setNombre(detalles.getNombre());
            TipoHabitacion actualizado = tipoHabitacionRepository.save(tipo);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLogico(@PathVariable Long id) {
        return tipoHabitacionRepository.findById(id).map(tipo -> {
            tipo.setActivo(false);
            tipoHabitacionRepository.save(tipo);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
