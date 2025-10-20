package com.primopato.api.service;

import com.primopato.api.record.DropDownResponse;
import com.primopato.api.repository.DroneRepository;
import com.primopato.api.repository.FabricanteDroneRepository;
import com.primopato.api.repository.ModeloDroneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DroneService {

    private final DroneRepository droneRepository;
    private final FabricanteDroneRepository fabricanteDroneRepository;
    private final ModeloDroneRepository modeloDroneRepository;

    public List<DropDownResponse> carregarFabricantes(Long idPais) {
        return fabricanteDroneRepository.findAllByPais_Id(idPais)
                .stream()
                .map(fabricanteDrone -> new DropDownResponse(fabricanteDrone.getId().toString(), fabricanteDrone.getNome()))
                .toList();
    }

    public List<DropDownResponse> carregarModelos(Long idFabricante) {
        return modeloDroneRepository.findAllByFabricante_Id(idFabricante)
                .stream()
                .map(modeloDrone -> new DropDownResponse(modeloDrone.getId().toString(), modeloDrone.getNome()))
                .toList();
    }

    public List<DropDownResponse> carregarNumerosSerie(Long idModelo) {
        return droneRepository.findAllByModelo_Id(idModelo)
                .stream()
                .map(drone -> new DropDownResponse(drone.getId().toString(), drone.getNumeroSerie()))
                .toList();
    }
}
