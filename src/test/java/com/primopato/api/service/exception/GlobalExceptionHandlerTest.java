package com.primopato.api.service.exception;

import com.primopato.api.exception.GlobalExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void deveRetornarUnauthorized_quandoAuthenticationException() {
        AuthenticationException ex = mock(AuthenticationException.class);
        when(ex.getMessage()).thenReturn("Credenciais inválidas");

        ResponseEntity<String> response = exceptionHandler.handleAuthentication(ex);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Credenciais inválidas", response.getBody());
    }

    @Test
    void deveRetornarNotFound_quandoUsernameNotFoundException() {
        UsernameNotFoundException ex = new UsernameNotFoundException("Usuário não encontrado");

        ResponseEntity<String> response = exceptionHandler.handleUsernameNotFound(ex);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void deveRetornarNotFound_quandoEntityNotFoundException() {
        EntityNotFoundException ex = new EntityNotFoundException("Entidade não encontrada");

        ResponseEntity<String> response = exceptionHandler.handleEntityNotFound(ex);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void deveRetornarBadRequest_quandoInvalidParameterException() {
        InvalidParameterException ex = new InvalidParameterException("Parâmetro inválido");

        ResponseEntity<String> response = exceptionHandler.handleInvalidParameter(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Parâmetro inválido", response.getBody());
    }

    @Test
    void deveRetornarBadRequest_quandoMethodArgumentNotValidException() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getMessage()).thenReturn("Erro de validação");

        ResponseEntity<String> response = exceptionHandler.handleMethodArgumentNotValid(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Erro de validação", response.getBody());
    }

    @Test
    void deveRetornarInternalServerError_quandoRuntimeException() {
        RuntimeException ex = new RuntimeException("Erro inesperado");

        ResponseEntity<String> response = exceptionHandler.handleRuntimeException(ex);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Erro inesperado", response.getBody());
    }
}

