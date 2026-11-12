
package com.hotel.sicig.service;

import com.hotel.sicig.exception.ResourceNotFoundException;
import com.hotel.sicig.model.ComprobanteSunat;
import com.hotel.sicig.model.Reserva;
import com.hotel.sicig.repository.ComprobanteSunatRepository;
import com.hotel.sicig.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class SunatService {

    private final ComprobanteSunatRepository comprobanteRepository;

    private final ReservaRepository reservaRepository;

    SunatService(ComprobanteSunatRepository comprobanteRepository, ReservaRepository reservaRepository) {
        this.comprobanteRepository = comprobanteRepository;
        this.reservaRepository = reservaRepository;
    }

    public ComprobanteSunat emitirComprobante(Long reservaId, String tipoComprobante, String serieCorrelativo) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada para emitir comprobante"));

        BigDecimal montoBase = reserva.getMontoTotal();
        BigDecimal igv = BigDecimal.ZERO;
        BigDecimal total = montoBase;

        
        boolean esExtranjero = reserva.getCliente().getExtranjero();
        
        BigDecimal subtotal;
        

        if (!esExtranjero) {
            
             subtotal = montoBase.divide(new BigDecimal("1.18"), 2, RoundingMode.HALF_UP);
            igv = montoBase.subtract(subtotal);
        } else {
            subtotal = montoBase;
            igv = BigDecimal.ZERO;
        }

        ComprobanteSunat comprobante = new ComprobanteSunat();
        comprobante.setReserva(reserva);
        comprobante.setTipoComprobante(tipoComprobante);
        comprobante.setSerieCorrelativo(serieCorrelativo);
        comprobante.setSubtotal(subtotal);
        comprobante.setIgv(igv);
        comprobante.setMontoTotal(total);

        return comprobanteRepository.save(comprobante);
    }
}