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
import java.security.SecureRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

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

    public void resetarSenha(String email) {
        Usuario usuario = usuarioRepository.findByUsuario(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o e-mail: " + email));

        String novaSenha = gerarSenhaAleatoria();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        emailService.enviarNovaSenha(email, novaSenha);
    }

    private String gerarSenhaAleatoria() {
        String maiusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String minusculas = "abcdefghijklmnopqrstuvwxyz";
        String numeros = "0123456789";
        String especiais = "!@#$%&*";
        String todosCaracteres = maiusculas + minusculas + numeros + especiais;

        SecureRandom random = new SecureRandom();
        StringBuilder senha = new StringBuilder(8);

        senha.append(maiusculas.charAt(random.nextInt(maiusculas.length())));
        senha.append(minusculas.charAt(random.nextInt(minusculas.length())));
        senha.append(numeros.charAt(random.nextInt(numeros.length())));
        senha.append(especiais.charAt(random.nextInt(especiais.length())));

        for (int i = 4; i < 8; i++) {
            senha.append(todosCaracteres.charAt(random.nextInt(todosCaracteres.length())));
        }

        char[] caracteres = senha.toString().toCharArray();
        for (int i = caracteres.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = caracteres[i];
            caracteres[i] = caracteres[j];
            caracteres[j] = temp;
        }

        return new String(caracteres);
    }
}