package com.hotel.sicig.repository;

import com.hotel.sicig.model.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TipoHabitacionRepository extends JpaRepository<TipoHabitacion, Long> {
    List<TipoHabitacion> findByActivoTrue();
}
