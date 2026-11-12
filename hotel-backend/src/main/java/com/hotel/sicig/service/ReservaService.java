
package com.hotel.sicig.service;

import com.hotel.sicig.exception.ResourceNotFoundException;
import com.hotel.sicig.model.Habitacion;
import com.hotel.sicig.model.Reserva;
import com.hotel.sicig.repository.HabitacionRepository;
import com.hotel.sicig.repository.ReservaRepository;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    private final HabitacionRepository habitacionRepository;

    ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
        this.habitacionRepository = null;
    }

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public Reserva obtenerPorId(@SuppressWarnings("deprecation") @NonNull Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));
    }

    public Reserva guardar(Reserva reserva) {
        
        Habitacion habitacion = habitacionRepository.findById(reserva.getHabitacion().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada para la reserva"));

        habitacion.setEstado("RESERVADA");
        habitacionRepository.save(habitacion);

        return reservaRepository.save(reserva);
    }

    @SuppressWarnings("deprecation")
    public Reserva actualizarEstado(@NonNull Long id, String nuevoEstado) {
        Reserva reserva = obtenerPorId(id);
        reserva.setEstado(nuevoEstado);
        return reservaRepository.save(reserva);
    }

    @SuppressWarnings("deprecation")
    public void eliminar(@NonNull Long id) {
        Reserva reserva = obtenerPorId(id);
        reservaRepository.delete(reserva);
    }
}