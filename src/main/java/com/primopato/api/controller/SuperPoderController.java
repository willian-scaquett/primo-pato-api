package com.primopato.api.controller;

import com.primopato.api.enumerated.TipoSuperPoder;
import com.primopato.api.record.DropDownResponse;
import com.primopato.api.service.SuperPoderService;
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
@RequestMapping("superpoder")
public class SuperPoderController {

    private final SuperPoderService superPoderService;

    @Operation(summary = "Endpoint para carregar tipos de super poder")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipos de super poder carregados com sucesso")
    })
    @GetMapping("/tipos/carregar")
    public ResponseEntity<List<DropDownResponse>> carregarTipos() {
        return ResponseEntity.ok(superPoderService.carregarTipos());
    }

    @Operation(summary = "Endpoint para listar todos super poderes de um tipo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Super poderes buscados com sucesso")
    })
    @GetMapping("/tipo/{tipoSuperPoder}/carregar")
    public ResponseEntity<List<DropDownResponse>> carregarSuperPoderes(@PathVariable TipoSuperPoder tipoSuperPoder) {
        return ResponseEntity.ok(superPoderService.carregarSuperPoderes(tipoSuperPoder));
    }

}
