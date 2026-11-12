
package com.hotel.sicig.repository;

import com.hotel.sicig.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    Optional<Habitacion> findByNumeroHabitacion(String numeroHabitacion);

    List<Habitacion> findByEstado(String estado);
}