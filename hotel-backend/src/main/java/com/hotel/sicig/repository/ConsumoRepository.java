
package com.hotel.sicig.repository;

import com.hotel.sicig.model.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ConsumoRepository extends JpaRepository<Consumo, Long> {

    List<Consumo> findByReservaId(Long reservaId);
}