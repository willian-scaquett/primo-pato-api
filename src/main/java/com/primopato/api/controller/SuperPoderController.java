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
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("superpoder")
public class SuperPoderController {

    private final SuperPoderService superPoderService;

    @Operation(summary = "Endpoint para carregar tipos de super-poder")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipos de super poder carregados com sucesso")
    })
    @GetMapping("/tipo")
    public ResponseEntity<List<DropDownResponse>> listarTipos() {
        return ResponseEntity.ok(
                Stream.of(TipoSuperPoder.values())
                        .map(t -> new DropDownResponse(t.name(), t.getNome()))
                        .toList()
        );
    }

    @Operation(summary = "Endpoint para listar super-poderes de um tipo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Super poderes buscados com sucesso")
    })
    @GetMapping("/tipo/{tipoSuperPoder}")
    public ResponseEntity<List<DropDownResponse>> listarSuperPoderes(@PathVariable TipoSuperPoder tipoSuperPoder) {
        return ResponseEntity.ok(
                superPoderService.carregarSuperPoderes(tipoSuperPoder)
                        .stream()
                        .map(s -> new DropDownResponse(s.getId().toString(), s.getNome()))
                        .toList()
        );
    }
}
