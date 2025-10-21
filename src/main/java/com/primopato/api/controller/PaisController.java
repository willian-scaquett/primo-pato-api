package com.primopato.api.controller;

import com.primopato.api.record.DropDownResponse;
import com.primopato.api.service.PaisService;
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
@RequestMapping("pais")
public class PaisController {

    private final PaisService paisService;

    @Operation(summary = "Endpoint para listar todos países cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Países buscados com sucesso")
    })
    @GetMapping("/carregar")
    public ResponseEntity<List<DropDownResponse>> carregar() {
        return ResponseEntity.ok(paisService.carregar());
    }

}
