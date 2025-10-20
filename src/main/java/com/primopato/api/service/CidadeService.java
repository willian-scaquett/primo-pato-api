package com.primopato.api.service;

import com.primopato.api.entity.Cidade;
import com.primopato.api.entity.Estado;
import com.primopato.api.repository.CidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    public Cidade cadastrarCidade(String nome, Estado estado) {
        return cidadeRepository.save(new Cidade(nome.toUpperCase(), estado));
    }
}
