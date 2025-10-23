package com.primopato.api.service;

import com.primopato.api.entity.Pais;
import com.primopato.api.repository.PaisRepository;
import com.primopato.api.utils.CustomStringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaisService {

    private final PaisRepository paisRepository;

    public List<Pais> listarPaises() {
        log.info("Buscando países");
        return paisRepository.findAllByOrderByNomeAsc();
    }

    public Pais obterOuCriarPais(String nome) {
        log.info("Buscando país {}", nome);
        String nomeFormatado = CustomStringUtils.formataIncialMaiuscula(nome);
        return paisRepository.findByNome(nomeFormatado).orElseGet(() -> {
            log.info("País não encontrado. Criando novo: {}", nomeFormatado);
            return paisRepository.save(new Pais(nomeFormatado));
        });
    }
}
