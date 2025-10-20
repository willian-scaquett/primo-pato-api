package com.primopato.api.service.assembler;

import com.primopato.api.entity.*;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.repository.DroneRepository;
import com.primopato.api.repository.FabricanteDroneRepository;
import com.primopato.api.repository.ModeloDroneRepository;
import com.primopato.api.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DroneAssembler {

    private final FabricanteDroneRepository fabricanteDroneRepository;
    private final ModeloDroneRepository modeloDroneRepository;
    private final DroneRepository droneRepository;

    public Drone obterOuCriarDrone(PatoRequest request, Pais pais) {
        String fabricanteNome = StringUtils.formataIncialMaiuscula(request.fabricanteDrone());
        String modeloNome = StringUtils.formataIncialMaiuscula(request.modeloDrone());
        String numeroSerie = request.numeroSerieDrone().toUpperCase();

        FabricanteDrone fabricante = fabricanteDroneRepository
                .findByNomeAndPais(fabricanteNome, pais)
                .orElseGet(() -> fabricanteDroneRepository.save(new FabricanteDrone(fabricanteNome, pais)));

        ModeloDrone modelo = modeloDroneRepository
                .findByNomeAndFabricante(modeloNome, fabricante)
                .orElseGet(() -> modeloDroneRepository.save(new ModeloDrone(modeloNome, fabricante)));

        return droneRepository
                .findByNumeroSerieAndModelo(numeroSerie, modelo)
                .orElseGet(() -> droneRepository.save(new Drone(numeroSerie, modelo)));
    }
}
