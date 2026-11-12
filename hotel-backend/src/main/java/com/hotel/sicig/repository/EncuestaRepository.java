
package com.hotel.sicig.repository;

import com.hotel.sicig.model.EncuestaSatisfaccion;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;



public interface EncuestaRepository extends JpaRepository<EncuestaSatisfaccion, Long> {

    List<EncuestaSatisfaccion> findByClienteId(Long clienteId);
}

