package com.primopato.api.service;

import com.primopato.api.entity.Pais;
import com.primopato.api.repository.PaisRepository;
import com.primopato.api.utils.StringUtils;
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
        return paisRepository.findAll();
    }

    public Pais obterOuCriarPais(String nome) {
        String nomeFormatado = StringUtils.formataIncialMaiuscula(nome);
        return paisRepository.findByNome(nomeFormatado).orElseGet(() -> {
            log.info("País não encontrado. Criando novo: {}", nomeFormatado);
            return paisRepository.save(new Pais(nomeFormatado));
        });
    }
}
