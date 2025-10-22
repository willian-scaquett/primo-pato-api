package com.primopato.api.service;

import com.primopato.api.entity.Drone;
import com.primopato.api.entity.FabricanteDrone;
import com.primopato.api.entity.ModeloDrone;
import com.primopato.api.entity.Pais;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.repository.DroneRepository;
import com.primopato.api.repository.FabricanteDroneRepository;
import com.primopato.api.repository.ModeloDroneRepository;
import com.primopato.api.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DroneService {

    private final DroneRepository droneRepository;
    private final FabricanteDroneRepository fabricanteDroneRepository;
    private final ModeloDroneRepository modeloDroneRepository;

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

    public List<FabricanteDrone> listarFabricantes(Long idPais) {
        return fabricanteDroneRepository.findAllByPais_Id(idPais);
    }

    public List<ModeloDrone> listarModelos(Long idFabricante) {
        return modeloDroneRepository.findAllByFabricante_Id(idFabricante);
    }

    public List<Drone> listarDrones(Long idModelo) {
        return droneRepository.findAllByModelo_Id(idModelo);
    }
}
