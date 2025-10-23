package com.primopato.api.service;

import com.primopato.api.entity.Usuario;
import com.primopato.api.record.CadastroUsuarioRequest;
import com.primopato.api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.InvalidParameterException;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario criarUsuario(CadastroUsuarioRequest cadastroUsuarioRequest) {
        if (usuarioRepository.existsByUsuario(cadastroUsuarioRequest.usuario())) {
            log.warn("Usuário já existe");
            throw new InvalidParameterException("Usuário já existe");
        }

        log.info("Cadastrando novo usuário");

        Usuario usuario = new Usuario();
        usuario.setUsuario(cadastroUsuarioRequest.usuario());
        usuario.setSenha(passwordEncoder.encode(cadastroUsuarioRequest.senha()));
        usuario.setNome(cadastroUsuarioRequest.nome());

        return usuarioRepository.save(usuario);
    }

    public Usuario getUsuario(String usuario) {
        log.info("Buscando usuário {}", usuario);
        return usuarioRepository.findByUsuario(usuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }
}