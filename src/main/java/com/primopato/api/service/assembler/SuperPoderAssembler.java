package com.primopato.api.service.assembler;

import com.primopato.api.entity.SuperPoder;
import com.primopato.api.enumerated.TipoSuperPoder;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.repository.SuperPoderRepository;
import com.primopato.api.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuperPoderAssembler {

    private final SuperPoderRepository superPoderRepository;

    public SuperPoder obterOuCriarSuperPoder(String nome, TipoSuperPoder tipo) {
        String nomeFormatado = StringUtils.formataIncialMaiuscula(nome);
        return superPoderRepository
                .findByNomeAndTipo(nome, tipo)
                .orElseGet(() -> superPoderRepository.save(new SuperPoder(nomeFormatado, tipo)));
    }
}
