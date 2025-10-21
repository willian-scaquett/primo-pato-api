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

    public Pato montarPato(PatoRequest patoRequest, Usuario usuario, Pato patoExistente) {
        Pato pato = patoExistente == null ? new Pato() : patoExistente;

        pato.setUsuario(usuario);

        Pais pais = paisAssembler.obterOuCriarPais(patoRequest.paisDrone());
        Drone drone = droneAssembler.obterOuCriarDrone(patoRequest, pais);
        pato.setDroneQueEncontrou(drone);

        //realiza as conversões quando necessário
        boolean isEua = pais.equals(LocalizacaoUtils.EUA);
        pato.setAltura(isEua ? UnidadesUtils.peParaCentimetro(patoRequest.altura()) : patoRequest.altura());
        pato.setPeso(isEua ? UnidadesUtils.libraParaGrama(patoRequest.peso()) : patoRequest.peso());
        pato.setPrecisaoDoGpsQuandoEncontrado(isEua ? UnidadesUtils.jardaParaCentimetro(patoRequest.precisao()) : patoRequest.precisao());

        pato.setLocalizacao(localizacaoService.obterOuCriarLocalizacao(patoRequest.latitude(), patoRequest.longitude(),
                patoRequest.pais(), patoRequest.estado(), patoRequest.cidade(), patoRequest.pontoReferencia()));

        pato.setQuantidadeMutacoes(patoRequest.quantidadeMutacoes());
        pato.setEstadoHibernacao(patoRequest.estadoHibernacao());

        if (patoRequest.estadoHibernacao().equals(EstadoHibernacao.DESPERTO)) {
            pato.setSuperPoder(superPoderAssembler.obterOuCriarSuperPoder(patoRequest.nomeSuperPoder(), patoRequest.tipoSuperPoder()));
        } else {
            pato.setBpm(patoRequest.bpm());
        }

        return pato;
    }
}
