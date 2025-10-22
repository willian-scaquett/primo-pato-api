package com.primopato.api.controller;

import com.primopato.api.record.MissaoInfoResponse;
import com.primopato.api.service.MissaoInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("missaoinfo")
public class MissaoInfoController {

    private final MissaoInfoService missaoInfoService;

    @Operation(summary = "Endpoint para carregar as informações de missão de captura por id do pato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Informações de missão buscadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pato não encontrado para o usuário")
    })
    @GetMapping("/buscar/{idPato}")
    public ResponseEntity<MissaoInfoResponse> buscar(Authentication authentication, @PathVariable Long idPato) {
        return ResponseEntity.ok(missaoInfoService.buscarMissaoInfo(idPato, authentication.getName()));
    }
}
