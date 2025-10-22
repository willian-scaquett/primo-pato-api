package com.primopato.api.service;

import com.primopato.api.record.DropDownResponse;
import com.primopato.api.repository.PaisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaisService {

    private final PaisRepository paisRepository;

    public List<DropDownResponse> carregar() {
        return paisRepository.findAll()
                .stream()
                .map(pais -> new DropDownResponse(pais.getId().toString(), pais.getNome()))
                .toList();
    }
}
