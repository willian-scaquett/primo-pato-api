package com.primopato.api.service;

import com.primopato.api.entity.Pais;
import com.primopato.api.repository.PaisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaisService {

    private final PaisRepository paisRepository;

    public List<Pais> listarPaises() {
        return paisRepository.findAll();
    }
}
