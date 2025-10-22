package com.primopato.api.service;

import com.primopato.api.entity.Drone;
import com.primopato.api.entity.FabricanteDrone;
import com.primopato.api.entity.ModeloDrone;
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
