package com.primopato.api.service;

import com.primopato.api.entity.Usuario;
import com.primopato.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario criarUsuario(String usuario, String senha, String nome) {
        if (usuarioRepository.existsByUsuario(usuario)) {
            throw new RuntimeException("Username já existe");
        }

        Usuario u = new Usuario();
        u.setUsuario(usuario);
        u.setSenha(passwordEncoder.encode(senha));
        u.setNome(nome);

        return usuarioRepository.save(u);
    }

    public Usuario getUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}