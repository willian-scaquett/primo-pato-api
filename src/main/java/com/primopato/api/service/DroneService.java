package com.primopato.api.service;

import com.primopato.api.entity.Drone;
import com.primopato.api.entity.FabricanteDrone;
import com.primopato.api.entity.ModeloDrone;
import com.primopato.api.entity.Pais;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.repository.DroneRepository;
import com.primopato.api.repository.FabricanteDroneRepository;
import com.primopato.api.repository.ModeloDroneRepository;
import com.primopato.api.utils.CustomStringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DroneService {

    private final DroneRepository droneRepository;
    private final FabricanteDroneRepository fabricanteDroneRepository;
    private final ModeloDroneRepository modeloDroneRepository;

    public Drone obterOuCriarDrone(PatoRequest request, Pais pais) {
        log.info("Buscando drone com número de série {}", request.numeroSerieDrone());
        String numeroSerie = request.numeroSerieDrone().toUpperCase();

        FabricanteDrone fabricante = obterOuCriarFabricante(request.fabricanteDrone(), pais);
        ModeloDrone modelo = obterOuCriarModelo(request.modeloDrone(), fabricante);

        return droneRepository
                .findByNumeroSerieAndModelo(numeroSerie, modelo)
                .orElseGet(() -> {
                    log.info("Drone não encontrado. Criando novo com número de série: {}", numeroSerie);
                    return droneRepository.save(new Drone(numeroSerie, modelo));
                });
    }

    public FabricanteDrone obterOuCriarFabricante(String nome, Pais pais) {
        log.info("Buscando fabricante de drone {}", nome);
        String nomeFormatado = CustomStringUtils.formataIncialMaiuscula(nome);
        return fabricanteDroneRepository
                .findByNomeAndPais(nomeFormatado, pais)
                .orElseGet(() -> {
                    log.info("Fabricante de drone não encontrado. Criando novo: {}", nomeFormatado);
                    return fabricanteDroneRepository.save(new FabricanteDrone(nomeFormatado, pais));
                });
    }

    public ModeloDrone obterOuCriarModelo(String nome, FabricanteDrone fabricante) {
        log.info("Buscando modelo de drone {}", nome);
        String nomeFormatado = CustomStringUtils.formataIncialMaiuscula(nome);
        return modeloDroneRepository
                .findByNomeAndFabricante(nomeFormatado, fabricante)
                .orElseGet(() -> {
                    log.info("Modelo de drone não encontrado. Criando novo: {}", nomeFormatado);
                    return modeloDroneRepository.save(new ModeloDrone(nomeFormatado, fabricante));
                });
    }

    public List<FabricanteDrone> listarFabricantes(Long idPais) {
        log.info("Buscando fabricantes de drone do país {}", idPais);
        return fabricanteDroneRepository.findAllByPais_Id(idPais);
    }

    public List<ModeloDrone> listarModelos(Long idFabricante) {
        log.info("Buscando modelos de drone do fabricante {}", idFabricante);
        return modeloDroneRepository.findAllByFabricante_Id(idFabricante);
    }

    public List<Drone> listarDrones(Long idModelo) {
        log.info("Buscando drones do modelo {}", idModelo);
        return droneRepository.findAllByModelo_Id(idModelo);
    }
}
