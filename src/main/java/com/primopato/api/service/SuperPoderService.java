package com.primopato.api.service;

import com.primopato.api.entity.SuperPoder;
import com.primopato.api.enumerated.TipoSuperPoder;
import com.primopato.api.repository.SuperPoderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SuperPoderService {

    private final SuperPoderRepository superPoderRepository;

    public List<SuperPoder> carregarSuperPoderes(TipoSuperPoder tipoSuperPoder) {
        return superPoderRepository.findAllByTipo(tipoSuperPoder);
    }
}
