package com.hotel.sicig.repository;

import com.hotel.sicig.model.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface ComandaRepository extends JpaRepository<Comanda, Long> {
    List<Comanda> findByHabitacionId(Long habitacionId);
    List<Comanda> findByEstado(String estado);
}