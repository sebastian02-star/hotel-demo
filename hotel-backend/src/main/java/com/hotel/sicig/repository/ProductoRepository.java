package com.hotel.sicig.repository;

import com.hotel.sicig.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoriaAndDisponibleTrue(String categoria);
    List<Producto> findByDisponibleTrue();
}