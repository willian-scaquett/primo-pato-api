package com.primopato.api.record;

import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.enumerated.TipoSuperPoder;

public record PatoRequest(
        String numeroSerieDrone,
        String modeloDrone,
        String fabricanteDrone,
        String paisDrone,
        float altura,
        float peso,
        double latitude,
        double longitude,
        String endereco,
        float precisao,
        EstadoHibernacao estadoHibernacao,
        int bpm,
        int quantidadeMutacoes,
        String nomeSuperPoder,
        TipoSuperPoder tipoSuperPoder
) {}
