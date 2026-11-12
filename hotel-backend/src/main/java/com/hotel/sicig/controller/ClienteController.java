package com.hotel.sicig.controller;

import com.hotel.sicig.model.Cliente;
import com.hotel.sicig.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")

public class ClienteController {

    private final ClienteRepository clienteRepository;


    ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

 
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

  
    @PostMapping
    public Cliente registrarCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }


    @GetMapping("/documento/{numero}")
    public ResponseEntity<Cliente> obtenerPorDocumento(@PathVariable String numero) {
        return clienteRepository.findByNumeroDocumento(numero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
