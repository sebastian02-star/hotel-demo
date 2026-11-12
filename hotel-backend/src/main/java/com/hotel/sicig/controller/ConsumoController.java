

package com.hotel.sicig.controller;

import com.hotel.sicig.model.Consumo;
import com.hotel.sicig.service.ConsumoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consumos")

public class ConsumoController {

    private final ConsumoService consumoService;

    ConsumoController(ConsumoService consumoService) {
        this.consumoService = consumoService;
    }

    @GetMapping
    public List<Consumo> listarConsumos() {
        return consumoService.listarTodos();
    }

    @GetMapping("/reserva/{reservaId}")
    public List<Consumo> listarPorReserva(@PathVariable Long reservaId) {
        return consumoService.listarPorReserva(reservaId);
    }

    @PostMapping
    public ResponseEntity<Consumo> registrarConsumo(@RequestBody Consumo consumo) {
        return new ResponseEntity<>(consumoService.registrarConsumo(consumo), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConsumo(@PathVariable Long id) {
        consumoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
