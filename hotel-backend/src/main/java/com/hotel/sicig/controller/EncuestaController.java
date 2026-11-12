

package com.hotel.sicig.controller;

import com.hotel.sicig.model.EncuestaSatisfaccion;
import com.hotel.sicig.service.EncuestaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encuestas")

public class EncuestaController {

    private final EncuestaService encuestaService;

    EncuestaController(EncuestaService encuestaService) {
        this.encuestaService = encuestaService;
    }

    @GetMapping
    public List<EncuestaSatisfaccion> listarTodas() {
        return encuestaService.listarTodas();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<EncuestaSatisfaccion> listarPorCliente(@PathVariable Long clienteId) {
        return encuestaService.listarPorCliente(clienteId);
    }

    @PostMapping
    public ResponseEntity<EncuestaSatisfaccion> registrarEncuesta(@RequestBody EncuestaSatisfaccion encuesta) {
        return new ResponseEntity<>(encuestaService.registrarEncuesta(encuesta), HttpStatus.CREATED);
    }
}
