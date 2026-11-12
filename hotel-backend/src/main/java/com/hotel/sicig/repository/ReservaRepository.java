
package com.hotel.sicig.repository;

import com.hotel.sicig.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByEstado(String estado);

    List<Reserva> findByClienteId(Long clienteId);
}