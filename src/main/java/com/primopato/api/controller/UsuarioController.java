package com.primopato.api.controller;

import com.primopato.api.entity.Usuario;
import com.primopato.api.record.CadastroUsuarioRequest;
import com.primopato.api.record.LoginRequest;
import com.primopato.api.record.LoginResponse;
import com.primopato.api.security.JwtUtil;
import com.primopato.api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    @Operation(summary = "Endpoint para login")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @ApiResponse(responseCode = "401", description = "Dados de autenticação inválidos")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.usuario(),
                        loginRequest.senha()
                )
        );

        return ResponseEntity.ok(new LoginResponse(jwtUtil.generateToken(loginRequest.usuario())));
    }

    @Operation(summary = "Endpoint para cadastro de usuário")
    @ApiResponse(responseCode = "201", description = "Cadastro de usuário realizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Dados de cadastro de usuário inválidos")
    @PostMapping("/cadastrar")
    public ResponseEntity<LoginResponse> cadastrar(@RequestBody @Valid CadastroUsuarioRequest cadastroUsuarioRequest) {
        Usuario usuario = usuarioService.criarUsuario(cadastroUsuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponse(jwtUtil.generateToken(usuario.getUsername())));
    }
}
