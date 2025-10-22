package com.primopato.api.service;

import com.primopato.api.entity.Cidade;
import com.primopato.api.entity.Estado;
import com.primopato.api.repository.CidadeRepository;
import com.primopato.api.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    public Cidade obterOuCriarCidade(String nome, Estado estado) {
        String nomeFormatado = StringUtils.formataIncialMaiuscula(nome);
        return estado.getCidades().stream()
                .filter(c -> c.getNome().equals(nomeFormatado))
                .findFirst()
                .orElseGet(() -> cidadeRepository.save(new Cidade(nomeFormatado, estado)));
    }
}
