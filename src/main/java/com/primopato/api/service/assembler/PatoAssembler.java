package com.primopato.api.service.assembler;

import com.primopato.api.entity.*;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.service.LocalizacaoService;
import com.primopato.api.utils.LocalizacaoUtils;
import com.primopato.api.utils.UnidadesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatoAssembler {

    private final DroneAssembler droneAssembler;
    private final SuperPoderAssembler superPoderAssembler;
    private final PaisAssembler paisAssembler;
    private final LocalizacaoService localizacaoService;

    public Pato montarPato(PatoRequest request, Usuario usuario, Pato patoExistente) {
        Pato pato = patoExistente == null ? new Pato() : patoExistente;

        pato.setUsuario(usuario);

        Pais pais = paisAssembler.obterOuCriarPais(request.paisDrone());
        Drone drone = droneAssembler.obterOuCriarDrone(request, pais);
        pato.setDroneQueEncontrou(drone);

        //realiza as conversões quando necessário
        boolean isEua = pais.equals(LocalizacaoUtils.EUA);
        pato.setAltura(isEua ? UnidadesUtils.peParaCentimetro(request.altura()) : request.altura());
        pato.setPeso(isEua ? UnidadesUtils.libraParaGrama(request.peso()) : request.peso());
        pato.setPrecisaoDoGpsQuandoEncontrado(isEua ? UnidadesUtils.jardaParaCentimetro(request.precisao()) : request.precisao());

        pato.setLocalizacao(localizacaoService.obterOuCriarLocalizacao(request.latitude(), request.longitude()));

        pato.setQuantidadeMutacoes(request.quantidadeMutacoes());
        pato.setEstadoHibernacao(request.estadoHibernacao());

        if (request.estadoHibernacao().equals(EstadoHibernacao.DESPERTO)) {
            pato.setSuperPoder(superPoderAssembler.obterOuCriarSuperPoder(request.nomeSuperPoder(), request.tipoSuperPoder()));
        } else {
            pato.setBpm(request.bpm());
        }

        return pato;
    }
}
