package com.primopato.api.controller;

import com.primopato.api.entity.Pato;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.service.PatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("pato")
public class PatoController {

    private final PatoService patoService;

    @Operation(summary = "Endpoint teste")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "A API está bem")
    })
    @GetMapping("/teste/{teste}")
    public ResponseEntity<String> teste(@PathVariable String teste) {
        return ResponseEntity.ok(teste);
    }

    @Operation(summary = "Endpoint para cadastro de patos primordiais")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pato cadastrado com sucesso")
    })
    @PostMapping("/cadastrar")
    public ResponseEntity<Pato> cadastrarPato(@PathVariable PatoRequest patoRequest) {
        return ResponseEntity.ok(patoService.cadastrar(patoRequest));
    }
}
