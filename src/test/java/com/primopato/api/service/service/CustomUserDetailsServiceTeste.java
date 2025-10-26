//package com.primopato.api.service.service;
//
//import com.primopato.api.entity.Usuario;
//import com.primopato.api.repository.EstadoRepository;
//import com.primopato.api.repository.UsuarioRepository;
//import com.primopato.api.security.CustomUserDetailsService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//
//import java.util.Optional;
//
//import static org.mockito.Mockito.when;
//
//public class CustomUserDetailsServiceTeste {
//
//    @Mock
//    private UsuarioRepository estadoRepository;
//
//    @BeforeEach
//
//    @Test
//    void deveBuscarUsuarioComSucesso() throws  Exception {
//        when(estadoRepository.findByUsuario("teste")).thenReturn(Optional.of(new Usuario()));
//
//        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(estadoRepository);
//        customUserDetailsService.loadUserByUsername("teste");
//
//
//    }
//
//
//    }
