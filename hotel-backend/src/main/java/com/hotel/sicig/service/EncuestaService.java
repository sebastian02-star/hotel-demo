
package com.hotel.sicig.service;


import com.hotel.sicig.model.EncuestaSatisfaccion;
import com.hotel.sicig.repository.EncuestaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EncuestaService {

    private final EncuestaRepository encuestaRepository;

    EncuestaService(EncuestaRepository encuestaRepository) {
        this.encuestaRepository = encuestaRepository;
    }

    public List<EncuestaSatisfaccion> listarTodas() {
        return encuestaRepository.findAll();
    }

    public EncuestaSatisfaccion registrarEncuesta(EncuestaSatisfaccion encuesta) {
        return encuestaRepository.save(encuesta);
    }

    public List<EncuestaSatisfaccion> listarPorCliente(Long clienteId) {
        return encuestaRepository.findByClienteId(clienteId);
    }
}