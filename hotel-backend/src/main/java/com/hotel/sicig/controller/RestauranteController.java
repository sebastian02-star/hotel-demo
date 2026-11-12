package com.hotel.sicig.controller;

import com.hotel.sicig.dto.ComandaRequestDto;
import com.hotel.sicig.model.Comanda;
import com.hotel.sicig.model.Consumo;
import com.hotel.sicig.model.Producto;
import com.hotel.sicig.service.ConsumoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")

public class RestauranteController {

    private final ConsumoService consumoService;

    public RestauranteController(ConsumoService consumoService) {
        this.consumoService = consumoService;
    }

    @GetMapping("/menu")
    public ResponseEntity<List<Producto>> listarMenu() {
        return ResponseEntity.ok(consumoService.listarProductosDisponibles());
    }

    @PostMapping("/orders")
    public ResponseEntity<Comanda> registrarComanda(@RequestBody ComandaRequestDto requestDto) {
        Comanda nuevaComanda = consumoService.registrarComanda(requestDto);
        return ResponseEntity.ok(nuevaComanda);
    }

    @GetMapping("/consumos/{reservaId}")
    public ResponseEntity<List<Consumo>> listarConsumosReserva(@PathVariable Long reservaId) {
        return ResponseEntity.ok(consumoService.listarPorReserva(reservaId));
    }
}