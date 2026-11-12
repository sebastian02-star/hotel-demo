
package com.hotel.sicig.service;

import com.hotel.sicig.dto.CheckoutRequestDto;
import com.hotel.sicig.exception.ResourceNotFoundException;
import com.hotel.sicig.model.*;
import com.hotel.sicig.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CajaService {

    private final ReservaRepository reservaRepository;
    private final HabitacionRepository habitacionRepository;
    private final ConsumoRepository consumoRepository;

    public CajaService(ReservaRepository reservaRepository, 
                         HabitacionRepository habitacionRepository, 
                         ConsumoRepository consumoRepository) {
        this.reservaRepository = reservaRepository;
        this.habitacionRepository = habitacionRepository;
        this.consumoRepository = consumoRepository;
    }

    @SuppressWarnings("null")
@Transactional
    public String procesarCheckout(CheckoutRequestDto requestDto) {

        Habitacion habitacion = habitacionRepository.findById(requestDto.getHabitacionId())
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada"));

      
        Reserva reserva = reservaRepository.findByEstado("CONFIRMADA").stream()
                .filter(r -> r.getHabitacion().getId().equals(habitacion.getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No hay reserva activa para esta habitación"));

       
        long diasEstadia = ChronoUnit.DAYS.between(reserva.getFechaIngreso(), reserva.getFechaSalida());
        if (diasEstadia <= 0) diasEstadia = 1; 
        BigDecimal costoAlojamiento = habitacion.getPrecioNoche().multiply(BigDecimal.valueOf(diasEstadia));

        
        List<Consumo> consumos = consumoRepository.findByReservaId(reserva.getId());
        BigDecimal totalConsumos = consumos.stream()
                .map(Consumo::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

       
        BigDecimal totalPagar = costoAlojamiento.add(totalConsumos);

        habitacion.setEstado("LIMPIEZA");
        habitacionRepository.save(habitacion);

        reserva.setEstado("CHECK_OUT");
        reservaRepository.save(reserva);

        return "Checkout exitoso. Total cobrado: S/ " + totalPagar + " mediante " + requestDto.getMetodoPago() + ". Habitación enviada a limpieza.";
    }
}