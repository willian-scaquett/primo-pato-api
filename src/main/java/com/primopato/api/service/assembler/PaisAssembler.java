package com.primopato.api.service.assembler;

import com.primopato.api.entity.Pais;
import com.primopato.api.repository.PaisRepository;
import com.primopato.api.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaisAssembler {

    private final PaisRepository paisRepository;

    public Pais obterOuCriarPais(String nome) {
        String nomeFormatado = StringUtils.formataIncialMaiuscula(nome);
        return paisRepository.findByNome(nomeFormatado).orElseGet(() -> paisRepository.save(new Pais(nomeFormatado)));
    }
}
