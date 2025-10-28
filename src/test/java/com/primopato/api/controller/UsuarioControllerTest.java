package com.primopato.api.controller;

import com.primopato.api.entity.Usuario;
import com.primopato.api.record.*;
import com.primopato.api.security.JwtUtil;
import com.primopato.api.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Test
    void testLogin() {
        AuthenticationManager manager = mock(AuthenticationManager.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        UsuarioService usuarioService = mock(UsuarioService.class);
        UsuarioController controller = new UsuarioController(manager, jwtUtil, usuarioService);

        LoginRequest request = new LoginRequest("user", "123");
        when(jwtUtil.generateToken("user")).thenReturn("token123");

        ResponseEntity<LoginResponse> response = controller.login(request);

        verify(manager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("token123", response.getBody().token());
    }

    @Test
    void testCadastrar() {
        AuthenticationManager manager = mock(AuthenticationManager.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        UsuarioService usuarioService = mock(UsuarioService.class);
        UsuarioController controller = new UsuarioController(manager, jwtUtil, usuarioService);

        CadastroUsuarioRequest request = new CadastroUsuarioRequest("user", "123", "Will");
        Usuario usuario = new Usuario();
        usuario.setUsuario("user");
        usuario.setNome("Will");
        when(usuarioService.criarUsuario(request)).thenReturn(usuario);
        when(jwtUtil.generateToken("user")).thenReturn("jwtToken");

        ResponseEntity<LoginResponse> response = controller.cadastrar(request);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("jwtToken", response.getBody().token());
    }

    @Test
    void testEsqueciSenha() {
        AuthenticationManager manager = mock(AuthenticationManager.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        UsuarioService usuarioService = mock(UsuarioService.class);
        UsuarioController controller = new UsuarioController(manager, jwtUtil, usuarioService);

        EsqueciSenhaRequest req = new EsqueciSenhaRequest("user@teste.com");
        ResponseEntity<Response> resp = controller.esqueciSenha(req);

        verify(usuarioService).resetarSenha("user@teste.com");
        assertEquals(200, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertEquals("Nova senha enviada para o e-mail cadastrado", resp.getBody().message());
    }

    @Test
    void testEu() {
        AuthenticationManager manager = mock(AuthenticationManager.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        UsuarioService usuarioService = mock(UsuarioService.class);
        UsuarioController controller = new UsuarioController(manager, jwtUtil, usuarioService);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");
        Usuario usuario = new Usuario();
        usuario.setNome("Will");
        when(usuarioService.getUsuario("user")).thenReturn(usuario);

        ResponseEntity<Response> resp = controller.eu(auth);

        assertEquals(200, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertEquals("Will", resp.getBody().message());
    }

    @Test
    void testMudarSenha() {
        AuthenticationManager manager = mock(AuthenticationManager.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        UsuarioService usuarioService = mock(UsuarioService.class);
        UsuarioController controller = new UsuarioController(manager, jwtUtil, usuarioService);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");

        MudarSenhaRequest req = new MudarSenhaRequest("antiga", "nova");

        ResponseEntity<Response> resp = controller.mudarSenha(auth, req);

        verify(manager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioService).mudarSenha("user", req);
        assertEquals(200, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertEquals("Senha alterada com sucesso.", resp.getBody().message());
    }

    @Test
    void testApagar() {
        AuthenticationManager manager = mock(AuthenticationManager.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        UsuarioService usuarioService = mock(UsuarioService.class);
        UsuarioController controller = new UsuarioController(manager, jwtUtil, usuarioService);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");

        ResponseEntity<Response> resp = controller.apagar(auth);

        verify(usuarioService).apagar("user");
        assertEquals(204, resp.getStatusCodeValue());
        assertNull(resp.getBody());
    }
}
