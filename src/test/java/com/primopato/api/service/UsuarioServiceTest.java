package com.primopato.api.service;

import com.primopato.api.entity.Usuario;
import com.primopato.api.record.CadastroUsuarioRequest;
import com.primopato.api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.InvalidParameterException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;

    private CadastroUsuarioRequest cadastroRequest;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        cadastroRequest = new CadastroUsuarioRequest(
                "joaosilva",
                "senha123",
                "João Silva"
        );

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsuario("joaosilva");
        usuario.setSenha("$2a$10$encodedPassword");
        usuario.setNome("João Silva");
    }

    @Test
    void deveCriarUsuarioComSucesso() {
        when(usuarioRepository.existsByUsuario("joaosilva")).thenReturn(false);
        when(passwordEncoder.encode("senha123")).thenReturn("$2a$10$encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.criarUsuario(cadastroRequest);

        assertNotNull(resultado);
        assertEquals("joaosilva", resultado.getUsuario());
        assertEquals("$2a$10$encodedPassword", resultado.getSenha());
        assertEquals("João Silva", resultado.getNome());

        verify(usuarioRepository).existsByUsuario("joaosilva");
        verify(passwordEncoder).encode("senha123");
        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuarioSalvo = usuarioCaptor.getValue();
        assertEquals("joaosilva", usuarioSalvo.getUsuario());
        assertEquals("$2a$10$encodedPassword", usuarioSalvo.getSenha());
        assertEquals("João Silva", usuarioSalvo.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioJaExistir() {
        when(usuarioRepository.existsByUsuario("joaosilva")).thenReturn(true);

        InvalidParameterException exception = assertThrows(
                InvalidParameterException.class,
                () -> usuarioService.criarUsuario(cadastroRequest)
        );

        assertEquals("Usuário já existe", exception.getMessage());
        verify(usuarioRepository).existsByUsuario("joaosilva");
        verify(passwordEncoder, never()).encode(any());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveEncodarSenhaAoCriarUsuario() {
        when(usuarioRepository.existsByUsuario("joaosilva")).thenReturn(false);
        when(passwordEncoder.encode("senha123")).thenReturn("$2a$10$encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        usuarioService.criarUsuario(cadastroRequest);

        verify(passwordEncoder).encode("senha123");
        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuarioSalvo = usuarioCaptor.getValue();
        assertEquals("$2a$10$encodedPassword", usuarioSalvo.getSenha());
        assertNotEquals("senha123", usuarioSalvo.getSenha());
    }

    @Test
    void deveDefinirTodosCamposAoCriarUsuario() {
        when(usuarioRepository.existsByUsuario("joaosilva")).thenReturn(false);
        when(passwordEncoder.encode("senha123")).thenReturn("$2a$10$encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        usuarioService.criarUsuario(cadastroRequest);

        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuarioSalvo = usuarioCaptor.getValue();
        assertNotNull(usuarioSalvo.getUsuario());
        assertNotNull(usuarioSalvo.getSenha());
        assertNotNull(usuarioSalvo.getNome());
        assertEquals("joaosilva", usuarioSalvo.getUsuario());
        assertEquals("$2a$10$encodedPassword", usuarioSalvo.getSenha());
        assertEquals("João Silva", usuarioSalvo.getNome());
    }

    @Test
    void deveCriarUsuariosComNomesUsuarioDiferentes() {
        CadastroUsuarioRequest request2 = new CadastroUsuarioRequest(
                "mariasilva",
                "senha456",
                "Maria Silva"
        );

        Usuario usuario2 = new Usuario();
        usuario2.setUsuario("mariasilva");
        usuario2.setSenha("$2a$10$encodedPassword2");
        usuario2.setNome("Maria Silva");

        when(usuarioRepository.existsByUsuario("mariasilva")).thenReturn(false);
        when(passwordEncoder.encode("senha456")).thenReturn("$2a$10$encodedPassword2");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario2);

        Usuario resultado = usuarioService.criarUsuario(request2);

        assertNotNull(resultado);
        assertEquals("mariasilva", resultado.getUsuario());
        assertEquals("Maria Silva", resultado.getNome());
        verify(usuarioRepository).existsByUsuario("mariasilva");
    }

    @Test
    void deveRetornarUsuarioQuandoEncontrado() {
        when(usuarioRepository.findByUsuario("joaosilva")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.getUsuario("joaosilva");

        assertNotNull(resultado);
        assertEquals(usuario, resultado);
        assertEquals("joaosilva", resultado.getUsuario());
        assertEquals("João Silva", resultado.getNome());
        verify(usuarioRepository).findByUsuario("joaosilva");
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(usuarioRepository.findByUsuario("usuarioInexistente"))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> usuarioService.getUsuario("usuarioInexistente")
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(usuarioRepository).findByUsuario("usuarioInexistente");
    }

    @Test
    void deveBuscarUsuarioPorNomeUsuarioExato() {
        when(usuarioRepository.findByUsuario("joaosilva")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.getUsuario("joaosilva");

        assertNotNull(resultado);
        assertEquals("joaosilva", resultado.getUsuario());
        verify(usuarioRepository).findByUsuario("joaosilva");
    }

    @Test
    void deveRetornarUsuarioComTodosCamposPreenchidos() {
        when(usuarioRepository.findByUsuario("joaosilva")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.getUsuario("joaosilva");

        assertNotNull(resultado.getId());
        assertNotNull(resultado.getUsuario());
        assertNotNull(resultado.getSenha());
        assertNotNull(resultado.getNome());
        assertEquals(1L, resultado.getId());
        assertEquals("joaosilva", resultado.getUsuario());
        assertEquals("$2a$10$encodedPassword", resultado.getSenha());
        assertEquals("João Silva", resultado.getNome());
    }

    @Test
    void deveCriarUsuarioComSenhasDiferentes() {
        CadastroUsuarioRequest request1 = new CadastroUsuarioRequest(
                "user1", "senha123", "User One"
        );
        CadastroUsuarioRequest request2 = new CadastroUsuarioRequest(
                "user2", "senha456", "User Two"
        );

        when(usuarioRepository.existsByUsuario("user1")).thenReturn(false);
        when(usuarioRepository.existsByUsuario("user2")).thenReturn(false);
        when(passwordEncoder.encode("senha123")).thenReturn("$encoded1");
        when(passwordEncoder.encode("senha456")).thenReturn("$encoded2");
        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        usuarioService.criarUsuario(request1);
        usuarioService.criarUsuario(request2);

        verify(passwordEncoder).encode("senha123");
        verify(passwordEncoder).encode("senha456");
        verify(usuarioRepository, times(2)).save(any(Usuario.class));
    }

    @Test
    void naoDevePermitirCriarUsuarioDuplicado() {
        when(usuarioRepository.existsByUsuario("joaosilva")).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("$2a$10$encoded");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        usuarioService.criarUsuario(cadastroRequest);

        when(usuarioRepository.existsByUsuario("joaosilva")).thenReturn(true);

        assertThrows(
                InvalidParameterException.class,
                () -> usuarioService.criarUsuario(cadastroRequest)
        );

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
}