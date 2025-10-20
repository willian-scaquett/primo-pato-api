package com.primopato.api.service;

import com.primopato.api.entity.Pato;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.enumerated.TipoSuperPoder;
import com.primopato.api.record.DropDownResponse;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.record.PatoResponse;
import com.primopato.api.repository.PatoRepository;
import com.primopato.api.repository.SuperPoderRepository;
import com.primopato.api.service.assembler.PatoAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class SuperPoderService {

    private final SuperPoderRepository superPoderRepository;

    public List<DropDownResponse> carregarTipos() {
        return Stream.of(TipoSuperPoder.values())
                .map(t -> new DropDownResponse(t.name(), t.getNome()))
                .toList();
    }

    public List<DropDownResponse> carregarSuperPoderes(TipoSuperPoder tipoSuperPoder) {
        return superPoderRepository.findAllByTipo(tipoSuperPoder)
                .stream()
                .map(s -> new DropDownResponse(s.getId().toString(), s.getNome()))
                .toList();
    }
}
