package com.primopato.api.service;

import com.primopato.api.entity.*;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.repository.*;
import com.primopato.api.utils.LocalizacaoUtils;
import com.primopato.api.utils.UnidadesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PatoService {

    private final DroneRepository droneRepository;
    private final FabricanteDroneRepository fabricanteDroneRepository;
    private final ModeloDroneRepository modeloDroneRepository;
    private final LocalizacaoService localizacaoService;
    private final PaisService paisService;
    private final PatoRepository patoRepository;
    private final SuperPoderRepository superPoderRepository;

    public Pato cadastrar(PatoRequest patoRequest) {
        Pais pais = paisService.getPais(patoRequest.paisDrone());
        FabricanteDrone fabricanteDrone = fabricanteDroneRepository.findByNomeAndPais(patoRequest.fabricanteDrone().toUpperCase(), pais).orElse(fabricanteDroneRepository.save(new FabricanteDrone(patoRequest.fabricanteDrone().toUpperCase(), pais)));
        ModeloDrone modeloDrone = modeloDroneRepository.findByNomeAndFabricante(patoRequest.modeloDrone().toUpperCase(), fabricanteDrone).orElse(modeloDroneRepository.save(new ModeloDrone(patoRequest.modeloDrone().toUpperCase(), fabricanteDrone)));
        Drone drone = droneRepository.findByNumeroSerieAndModelo(patoRequest.numeroSerieDrone().toUpperCase(), modeloDrone).orElse(droneRepository.save(new Drone(patoRequest.numeroSerieDrone(), modeloDrone)));

        Pato pato = new Pato();
        pato.setDroneQueEncontrou(drone);

        //realiza as conversões quando necessário
        boolean isEua = pais.equals(LocalizacaoUtils.EUA);
        pato.setAltura(isEua ? UnidadesUtils.peParaCentimetro(patoRequest.altura()) : patoRequest.altura());
        pato.setPeso(isEua ? UnidadesUtils.libraParaGrama(patoRequest.peso()) : patoRequest.peso());
        pato.setPrecisaoDoGpsQuandoEncontrado(isEua ? UnidadesUtils.jardaParaCentimetro(patoRequest.precisao()) : patoRequest.precisao());

        pato.setLocalizacao(localizacaoService.getLocalizaoPorCoordenada(patoRequest.latitude(), patoRequest.longitude()));

        pato.setEstadoHibernacao(patoRequest.estadoHibernacao());
        if (patoRequest.estadoHibernacao().equals(EstadoHibernacao.EM_TRANSE)
                || patoRequest.estadoHibernacao().equals(EstadoHibernacao.HIBERNACAO_PROFUNDA)) {
            pato.setBpm(patoRequest.bpm());
        }

        pato.setQuantidadeMutacoes(patoRequest.quantidadeMutacoes());

        SuperPoder superPoder = superPoderRepository.findByNomeAndTipo(patoRequest.nomeSuperPoder().toUpperCase(), patoRequest.tipoSuperPoder()).orElse(superPoderRepository.save(new SuperPoder(patoRequest.nomeSuperPoder(), patoRequest.tipoSuperPoder())));

        pato.setSuperPoder(superPoder);

        return patoRepository.save(pato);
    }
}
