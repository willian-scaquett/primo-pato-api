package com.primopato.api.controller;

import com.primopato.api.record.DropDownResponse;
import com.primopato.api.service.DroneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("drone")
public class DroneController {

    private final DroneService droneService;

    @Operation(summary = "Endpoint para listar todos fabricantes de drone cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fabricantes buscados com sucesso")
    })
    @GetMapping("/pais/{idPais}/fabricante")
    public ResponseEntity<List<DropDownResponse>> listarFabricantes(@PathVariable Long idPais) {
        return ResponseEntity.ok(
                droneService.listarFabricantes(idPais)
                        .stream()
                        .map(fabricanteDrone -> new DropDownResponse(fabricanteDrone.getId().toString(), fabricanteDrone.getNome()))
                        .toList()
        );
    }

    @Operation(summary = "Endpoint para listar todos modelos de drone cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modelos buscados com sucesso")
    })
    @GetMapping("/fabricante/{idFabricante}/modelo")
    public ResponseEntity<List<DropDownResponse>> listarModelos(@PathVariable Long idFabricante) {
        return ResponseEntity.ok(
                droneService.listarModelos(idFabricante)
                        .stream()
                        .map(modeloDrone -> new DropDownResponse(modeloDrone.getId().toString(), modeloDrone.getNome()))
                        .toList()
        );
    }

    @Operation(summary = "Endpoint para listar todos números de série de drone cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Números de série buscados com sucesso")
    })
    @GetMapping("/modelo/{idModelo}/numeroSerie")
    public ResponseEntity<List<DropDownResponse>> listarNumerosSerie(@PathVariable Long idModelo) {
        return ResponseEntity.ok(
                droneService.listarDrones(idModelo)
                        .stream()
                        .map(drone -> new DropDownResponse(drone.getId().toString(), drone.getNumeroSerie()))
                        .toList()
        );
    }
}
