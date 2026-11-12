package com.hotel.sicig.controller;

import com.hotel.sicig.model.Habitacion;
import com.hotel.sicig.service.HabitacionService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")

public class HabitacionController {

    private final HabitacionService habitacionService;

    HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @GetMapping
    public List<Habitacion> listarHabitaciones() {
        return habitacionService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(habitacionService.obtenerPorId(id));
    }

    @GetMapping("/estado/{estado}")
    public List<Habitacion> listarPorEstado(@PathVariable String estado) {
        return habitacionService.listarPorEstado(estado);
    }

    @PostMapping
    public ResponseEntity<Habitacion> registrarHabitacion(@Valid @RequestBody Habitacion habitacion) {
        Habitacion nuevaHabitacion = habitacionService.guardar(habitacion);
        return new ResponseEntity<>(nuevaHabitacion, HttpStatus.CREATED);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Habitacion> actualizarHabitacion(@PathVariable Long id, @RequestBody Habitacion habitacion) {
        
        habitacion.setId(id);
        Habitacion actualizada = habitacionService.guardar(habitacion);
        return ResponseEntity.ok(actualizada);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Habitacion> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(habitacionService.actualizarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHabitacion(@PathVariable Long id) {
        habitacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}