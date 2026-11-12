package com.hotel.sicig.controller;

import com.hotel.sicig.model.Usuario;
import com.hotel.sicig.repository.UsuarioRepository;
import com.hotel.sicig.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager, 
                          JwtTokenProvider tokenProvider, 
                          UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        System.out.println(">>> LOGIN JWT - USUARIO: [" + username + "]");

        try {
            
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            
            SecurityContextHolder.getContext().setAuthentication(authentication);

            
            String jwt = tokenProvider.generarToken(authentication);

           
            Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);

            if (usuario == null) {
                return ResponseEntity.status(401).body(Map.of("error", "Usuario no encontrado"));
            }

            
            return ResponseEntity.ok(Map.of(
                "token", jwt,
                "username", usuario.getUsername(),
                "nombres", usuario.getNombres(),
                "apellidos", usuario.getApellidos()
            ));

        } catch (BadCredentialsException e) {
            
            return ResponseEntity.status(401).body(Map.of("error", "Usuario o contraseña incorrectos"));
        }
        
    }
}
