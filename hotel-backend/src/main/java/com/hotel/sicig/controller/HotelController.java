package com.hotel.sicig.controller;

import com.hotel.sicig.dto.HabitacionEstadoRequestDto;
import com.hotel.sicig.dto.ReservaRequestDto;
import com.hotel.sicig.model.Cliente;
import com.hotel.sicig.model.Habitacion;
import com.hotel.sicig.model.Reserva;
import com.hotel.sicig.service.ClienteService;
import com.hotel.sicig.service.HabitacionService;
import com.hotel.sicig.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")

public class HotelController {

    private final HabitacionService habitacionService;
    private final ClienteService clienteService;
    private final ReservaService reservaService;

    public HotelController(HabitacionService habitacionService, ClienteService clienteService, ReservaService reservaService) {
        this.habitacionService = habitacionService;
        this.clienteService = clienteService;
        this.reservaService = reservaService;
    }



    @GetMapping("/rooms")
    public ResponseEntity<List<Habitacion>> listarHabitaciones() {
        return ResponseEntity.ok(habitacionService.listarTodas());
    }

    @PutMapping("/rooms/{id}/status")
    public ResponseEntity<Habitacion> actualizarEstadoHabitacion(
            @PathVariable Long id, 
            @RequestBody HabitacionEstadoRequestDto request) {
        Habitacion actualizada = habitacionService.actualizarEstado(id, request.getEstado());
        return ResponseEntity.ok(actualizada);
    }


    @GetMapping("/guests")
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @PostMapping("/guests")
    public ResponseEntity<Cliente> registrarCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.guardar(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }



    @GetMapping("/reservations")
    public ResponseEntity<List<Reserva>> listarReservas() {
        return ResponseEntity.ok(reservaService.listarTodas());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reserva> crearReserva(@RequestBody ReservaRequestDto requestDto) {

        Cliente cliente = clienteService.obtenerPorId(requestDto.getClienteId());
        Habitacion habitacion = habitacionService.obtenerPorId(requestDto.getHabitacionId());

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setHabitacion(habitacion);
        reserva.setFechaIngreso(requestDto.getFechaIngreso());
        reserva.setFechaSalida(requestDto.getFechaSalida());
        reserva.setCanalOrigen(requestDto.getCanalOrigen());
        reserva.setEstado("CONFIRMADA");
       
        reserva.setMontoTotal(habitacion.getPrecioNoche());

        Reserva nuevaReserva = reservaService.guardar(reserva);
        return ResponseEntity.ok(nuevaReserva);
    }
}
