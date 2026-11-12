

package com.hotel.sicig.controller;

import com.hotel.sicig.model.Reserva;
import com.hotel.sicig.service.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")

public class ReservaController {

    private final ReservaService reservaService;

    ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerPorId(@SuppressWarnings("deprecation") @PathVariable @NonNull Long id) {
        return ResponseEntity.ok(reservaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Reserva> registrarReserva(@RequestBody Reserva reserva) {
        return new ResponseEntity<>(reservaService.guardar(reserva), HttpStatus.CREATED);
    }

    @SuppressWarnings("deprecation")
    @PutMapping("/{id}/estado")
    public ResponseEntity<Reserva> cambiarEstado(@PathVariable @NonNull Long id, @RequestParam String estado) {
        return ResponseEntity.ok(reservaService.actualizarEstado(id, estado));
    }

    @SuppressWarnings("deprecation")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable @NonNull Long id) {
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
