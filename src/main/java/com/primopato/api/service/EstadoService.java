package com.primopato.api.service;

import com.primopato.api.entity.Estado;
import com.primopato.api.entity.Pais;
import com.primopato.api.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EstadoService {

    private final EstadoRepository estadoRepository;

    public Estado cadastrarEstado(String nome, Pais pais) {
        return estadoRepository.save(new Estado(nome.toUpperCase(), pais));
    }
}
