package com.primopato.api.service;

import com.primopato.api.entity.Usuario;
import com.primopato.api.record.CadastroUsuarioRequest;
import com.primopato.api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.InvalidParameterException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario criarUsuario(CadastroUsuarioRequest cadastroUsuarioRequest) {
        if (usuarioRepository.existsByUsuario(cadastroUsuarioRequest.usuario())) {
            throw new InvalidParameterException("Usuário já existe");
        }

        Usuario usuario = new Usuario();
        usuario.setUsuario(cadastroUsuarioRequest.usuario());
        usuario.setSenha(passwordEncoder.encode(cadastroUsuarioRequest.senha()));
        usuario.setNome(cadastroUsuarioRequest.nome());

        return usuarioRepository.save(usuario);
    }

    public Usuario getUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }
}