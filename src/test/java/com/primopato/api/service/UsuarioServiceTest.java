package com.primopato.api.service;

import com.primopato.api.entity.Usuario;
import com.primopato.api.record.CadastroUsuarioRequest;
import com.primopato.api.record.MudarSenhaRequest;
import com.primopato.api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.InvalidParameterException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Test
    void testCriarUsuario_Sucesso() {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        EmailService email = mock(EmailService.class);
        UsuarioService service = new UsuarioService(repo, encoder, email);

        CadastroUsuarioRequest req = new CadastroUsuarioRequest("user", "pass", "Nome");
        Usuario usuario = new Usuario();
        usuario.setUsuario("user");
        usuario.setSenha("hash");
        usuario.setNome("Nome");

        when(repo.existsByUsuario("user")).thenReturn(false);
        when(encoder.encode("pass")).thenReturn("hash");
        when(repo.save(any(Usuario.class))).thenReturn(usuario);

        Usuario result = service.criarUsuario(req);

        assertEquals("user", result.getUsuario());
        verify(repo).save(any(Usuario.class));
    }

    @Test
    void testCriarUsuario_UsuarioJaExiste() {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        EmailService email = mock(EmailService.class);
        UsuarioService service = new UsuarioService(repo, encoder, email);

        when(repo.existsByUsuario("user")).thenReturn(true);

        CadastroUsuarioRequest req = new CadastroUsuarioRequest("user", "123", "Nome");

        assertThrows(InvalidParameterException.class, () -> service.criarUsuario(req));
    }

    @Test
    void testGetUsuario_Encontrado() {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        EmailService email = mock(EmailService.class);
        UsuarioService service = new UsuarioService(repo, encoder, email);

        Usuario u = new Usuario();
        when(repo.findByUsuario("user")).thenReturn(Optional.of(u));

        Usuario result = service.getUsuario("user");
        assertSame(u, result);
    }

    @Test
    void testGetUsuario_NaoEncontrado() {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        EmailService email = mock(EmailService.class);
        UsuarioService service = new UsuarioService(repo, encoder, email);

        when(repo.findByUsuario("user")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getUsuario("user"));
    }

    @Test
    void testResetarSenha_Sucesso() {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        EmailService email = mock(EmailService.class);
        UsuarioService service = new UsuarioService(repo, encoder, email);

        Usuario u = new Usuario();
        when(repo.findByUsuario("user")).thenReturn(Optional.of(u));
        when(encoder.encode(anyString())).thenReturn("encoded");
        when(repo.save(u)).thenReturn(u);

        service.resetarSenha("user");

        verify(repo).save(u);
        verify(email).enviarNovaSenha(eq("user"), anyString());
    }

    @Test
    void testResetarSenha_NaoEncontrado() {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        EmailService email = mock(EmailService.class);
        UsuarioService service = new UsuarioService(repo, encoder, email);

        when(repo.findByUsuario("user")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.resetarSenha("user"));
    }

    @Test
    void testMudarSenha() {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        EmailService email = mock(EmailService.class);
        UsuarioService service = spy(new UsuarioService(repo, encoder, email));

        Usuario u = new Usuario();
        doReturn(u).when(service).getUsuario("user");
        when(encoder.encode("nova")).thenReturn("hash");
        when(repo.save(u)).thenReturn(u);

        MudarSenhaRequest req = new MudarSenhaRequest("antiga", "nova");
        service.mudarSenha("user", req);

        verify(repo).save(u);
        assertEquals("hash", u.getSenha());
    }

    @Test
    void testApagar() {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        EmailService email = mock(EmailService.class);
        UsuarioService service = spy(new UsuarioService(repo, encoder, email));

        Usuario u = new Usuario();
        doReturn(u).when(service).getUsuario("user");

        service.apagar("user");

        verify(repo).delete(u);
    }

    @Test
    void testGerarSenhaAleatoria() throws Exception {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        EmailService email = mock(EmailService.class);
        UsuarioService service = new UsuarioService(repo, encoder, email);

        var method = UsuarioService.class.getDeclaredMethod("gerarSenhaAleatoria");
        method.setAccessible(true);
        String senha = (String) method.invoke(service);

        assertNotNull(senha);
        assertEquals(8, senha.length());
    }
}
