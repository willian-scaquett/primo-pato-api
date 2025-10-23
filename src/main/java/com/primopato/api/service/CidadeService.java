package com.primopato.api.service;

import com.primopato.api.entity.Cidade;
import com.primopato.api.entity.Estado;
import com.primopato.api.repository.CidadeRepository;
import com.primopato.api.utils.CustomStringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    public Cidade obterOuCriarCidade(String nome, Estado estado) {
        log.info("Buscando cidade {} no estado {} no país {}", nome, estado.getNome(), estado.getPais().getNome());
        String nomeFormatado = CustomStringUtils.formataIncialMaiuscula(nome);
        return estado.getCidades().stream()
                .filter(c -> c.getNome().equals(nomeFormatado))
                .findFirst()
                .orElseGet(() -> {
                    log.info("Cidade não encontrada. Criando nova: {} - {} - {}", nome, estado.getNome(), estado.getPais().getNome());
                    return cidadeRepository.save(new Cidade(nomeFormatado, estado));
                });
    }
}
