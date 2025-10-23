package com.primopato.api.service;

import com.primopato.api.entity.Estado;
import com.primopato.api.entity.Pais;
import com.primopato.api.repository.EstadoRepository;
import com.primopato.api.utils.CustomStringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EstadoService {

    private final EstadoRepository estadoRepository;

    public Estado obterOuCriarEstado(String nome, Pais pais) {
        log.info("Buscando estado {} no país {}", nome, pais.getNome());
        String nomeFormatado = CustomStringUtils.formataIncialMaiuscula(nome);
        return pais.getEstados().stream()
                .filter(e -> e.getNome().equals(nomeFormatado))
                .findFirst()
                .orElseGet(() -> {
                    log.info("Estado não encontrado. Criando novo: {} - {}", nomeFormatado, pais.getNome());
                    return estadoRepository.save(new Estado(nomeFormatado, pais));
                });
    }
}
