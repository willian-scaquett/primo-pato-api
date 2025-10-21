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
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("drone")
public class DroneController {

    private final DroneService droneService;

    @Operation(summary = "Endpoint para listar todos fabricantes cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fabricantes buscados com sucesso")
    })
    @GetMapping("/pais/{idPais}/fabricante/carregar")
    public ResponseEntity<List<DropDownResponse>> carregarFabricantes(@PathVariable Long idPais) {
        return ResponseEntity.ok(droneService.carregarFabricantes(idPais));
    }

    @Operation(summary = "Endpoint para listar todos modelos cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modelos buscados com sucesso")
    })
    @GetMapping("/fabricante/{idFabricante}/modelo/carregar")
    public ResponseEntity<List<DropDownResponse>> carregarModelos(@PathVariable Long idFabricante) {
        return ResponseEntity.ok(droneService.carregarModelos(idFabricante));
    }

    @Operation(summary = "Endpoint para listar todos números de série cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Números de série buscados com sucesso")
    })
    @GetMapping("/modelo/{idModelo}/numeroSerie/carregar")
    public ResponseEntity<List<DropDownResponse>> carregarNumerosSerie(@PathVariable Long idModelo) {
        return ResponseEntity.ok(droneService.carregarNumerosSerie(idModelo));
    }

}
