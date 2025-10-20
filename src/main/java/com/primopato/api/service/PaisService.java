package com.primopato.api.service;

import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageResult;
import com.opencagedata.jopencage.model.JOpenCageReverseRequest;
import com.primopato.api.entity.Localizacao;
import com.primopato.api.entity.Pais;
import com.primopato.api.repository.PaisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaisService {

    private final PaisRepository paisRepository;

    public Pais getPais(String nome) {
        return paisRepository.findByNome(nome.toUpperCase()).orElse(paisRepository.save(new Pais(nome.toUpperCase())));
    }
}
