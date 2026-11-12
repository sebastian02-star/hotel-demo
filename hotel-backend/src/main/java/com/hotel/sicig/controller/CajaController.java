package com.hotel.sicig.controller;

import com.hotel.sicig.dto.CheckoutRequestDto;
import com.hotel.sicig.service.CajaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/caja")
public class CajaController {

    private final CajaService cajaService;

    public CajaController(CajaService cajaService) {
        this.cajaService = cajaService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> realizarCheckout(@RequestBody CheckoutRequestDto requestDto) {
        String resultado = cajaService.procesarCheckout(requestDto);
        return ResponseEntity.ok(resultado);
    }
}