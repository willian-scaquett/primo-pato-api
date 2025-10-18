package com.primopato.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pato")
@CrossOrigin
public class PatoController {

    @Operation(summary = "Endpoint teste")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "A API está bem")
    })
    @GetMapping("/teste/{teste}")
    public ResponseEntity<String> teste(@PathVariable String teste) {
        return ResponseEntity.ok(teste);
    }
}
