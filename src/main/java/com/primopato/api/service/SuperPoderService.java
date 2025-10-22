package com.primopato.api.service;

import com.primopato.api.entity.SuperPoder;
import com.primopato.api.enumerated.TipoSuperPoder;
import com.primopato.api.repository.SuperPoderRepository;
import com.primopato.api.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SuperPoderService {

    private final SuperPoderRepository superPoderRepository;

    public List<SuperPoder> carregarSuperPoderes(TipoSuperPoder tipoSuperPoder) {
        return superPoderRepository.findAllByTipo(tipoSuperPoder);
    }

    public SuperPoder obterOuCriarSuperPoder(String nome, TipoSuperPoder tipo) {
        String nomeFormatado = StringUtils.formataIncialMaiuscula(nome);
        return superPoderRepository
                .findByNomeAndTipo(nome, tipo)
                .orElseGet(() -> {
                    log.info("Super-poder não encontrado. Criando novo: {} - {}", tipo.getNome(), nomeFormatado);
                    return superPoderRepository.save(new SuperPoder(nomeFormatado, tipo));
                });
    }
}
