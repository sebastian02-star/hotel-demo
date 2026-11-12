package com.hotel.sicig.service;

import com.hotel.sicig.dto.ComandaRequestDto;
import com.hotel.sicig.exception.ResourceNotFoundException;
import com.hotel.sicig.model.*;
import com.hotel.sicig.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumoService {

    private final ConsumoRepository consumoRepository;

    private final ReservaRepository reservaRepository;

    private final ProductoRepository productoRepository;

    private final ComandaRepository comandaRepository;

    private final HabitacionRepository habitacionRepository;

    ConsumoService(ConsumoRepository consumoRepository, ReservaRepository reservaRepository, ProductoRepository productoRepository, ComandaRepository comandaRepository, HabitacionRepository habitacionRepository) {
        this.consumoRepository = consumoRepository;
        this.reservaRepository = reservaRepository;
        this.productoRepository = productoRepository;
        this.comandaRepository = comandaRepository;
        this.habitacionRepository = habitacionRepository;
    }

    public List<Consumo> listarTodos() {
        return consumoRepository.findAll();
    }

    public List<Consumo> listarPorReserva(Long reservaId) {
        return consumoRepository.findByReservaId(reservaId);
    }

    public List<Producto> listarProductosDisponibles() {
        return productoRepository.findByDisponibleTrue();
    }

    public Consumo registrarConsumo(Consumo consumo) {
        Reserva reserva = reservaRepository.findById(consumo.getReserva().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada para asociar el consumo"));

        consumo.setReserva(reserva);

        if (consumo.getCantidad() != null && consumo.getPrecioUnitario() != null) {
            BigDecimal subtotal = consumo.getPrecioUnitario().multiply(new BigDecimal(consumo.getCantidad()));
            consumo.setSubtotal(subtotal);
        }

        return consumoRepository.save(consumo);
    }

    public Comanda registrarComanda(ComandaRequestDto requestDto) {
        Habitacion habitacion = habitacionRepository.findById(requestDto.getHabitacionId())
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada"));

        Comanda comanda = new Comanda();
        comanda.setHabitacion(habitacion);
        comanda.setEstado("CARGADO_A_CUENTA");

        List<DetalleComanda> detalles = new ArrayList<>();
        BigDecimal montoTotalComanda = BigDecimal.ZERO;

        for (ComandaRequestDto.ItemComandaDto itemDto : requestDto.getItems()) {
            Producto producto = productoRepository.findById(itemDto.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado ID: " + itemDto.getProductoId()));

            BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(itemDto.getCantidad()));
            montoTotalComanda = montoTotalComanda.add(subtotal);

            DetalleComanda detalle = new DetalleComanda();
            detalle.setComanda(comanda);
            detalle.setProducto(producto);
            detalle.setCantidad(itemDto.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(subtotal);
            detalles.add(detalle);
        }

        comanda.setMontoTotal(montoTotalComanda);
        comanda.setItems(detalles);
        return comandaRepository.save(comanda);
    }

    public void eliminar(Long id) {
        Consumo consumo = consumoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consumo no encontrado con ID: " + id));
        consumoRepository.delete(consumo);
    }
}