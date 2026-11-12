
package com.hotel.sicig.service;

import com.hotel.sicig.exception.ResourceNotFoundException;
import com.hotel.sicig.model.Habitacion;
import com.hotel.sicig.repository.HabitacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;

    HabitacionService(HabitacionRepository habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }

    public List<Habitacion> listarTodas() {
        return habitacionRepository.findAll();
    }

    public Habitacion obtenerPorId(Long id) {
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada con ID: " + id));
    }

    public List<Habitacion> listarPorEstado(String estado) {
        return habitacionRepository.findByEstado(estado);
    }

    public Habitacion guardar(Habitacion habitacion) {
        return habitacionRepository.save(habitacion);
    }

    public Habitacion actualizarEstado(Long id, String nuevoEstado) {
        Habitacion habitacion = obtenerPorId(id);
        habitacion.setEstado(nuevoEstado);
        return habitacionRepository.save(habitacion);
    }

    public void eliminar(Long id) {
        Habitacion habitacion = obtenerPorId(id);
        habitacionRepository.delete(habitacion);
    }
}