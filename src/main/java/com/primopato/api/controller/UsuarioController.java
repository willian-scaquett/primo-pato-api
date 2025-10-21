package com.primopato.api.controller;

import com.primopato.api.entity.Usuario;
import com.primopato.api.record.CadastroUsuarioRequest;
import com.primopato.api.record.LoginRequest;
import com.primopato.api.record.LoginResponse;
import com.primopato.api.security.JwtUtil;
import com.primopato.api.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.usuario(),
                            loginRequest.senha()
                    )
            );

            String token = jwtUtil.generateToken(loginRequest.usuario());
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastroUsuarioRequest cadastroUsuarioRequest) {
        try {

            Usuario usuario = usuarioService.criarUsuario(
                    cadastroUsuarioRequest.usuario(),
                    cadastroUsuarioRequest.senha(),
                    cadastroUsuarioRequest.nome()
            );

            String token = jwtUtil.generateToken(usuario.getUsername());

            return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponse(
                    token
            ));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
