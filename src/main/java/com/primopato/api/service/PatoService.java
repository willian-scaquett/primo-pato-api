package com.primopato.api.service;

import com.primopato.api.entity.Pais;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.repository.PaisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PatoService {

    private final PaisRepository paisRepository;

    private void cadastrar(PatoRequest patoRequest) {
        Pais pais = paisRepository.findByNome(patoRequest.paisDrone()).orElse(paisRepository.save(new Pais(patoRequest.paisDrone().toUpperCase())));
    }
}
