package com.hotel.sicig.service;

import com.hotel.sicig.exception.ResourceNotFoundException;
import com.hotel.sicig.model.Cliente;
import com.hotel.sicig.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        
        return clienteRepository.findByActivoTrue();
    }

    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
    }

    public Cliente guardar(Cliente cliente) {
        if ("PASAPORTE".equalsIgnoreCase(cliente.getTipoDocumento()) || 
            "CARNET_EXTRANJERIA".equalsIgnoreCase(cliente.getTipoDocumento())) {
            cliente.setExtranjero(true);
        }
        return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {
        
        Cliente cliente = obtenerPorId(id);
        cliente.setActivo(false); 
        clienteRepository.save(cliente);
    }
}