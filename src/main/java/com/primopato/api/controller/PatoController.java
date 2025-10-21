package com.primopato.api.controller;

import com.primopato.api.entity.Pato;
import com.primopato.api.record.DropDownResponse;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.record.PatoResponse;
import com.primopato.api.service.MissaoInfoService;
import com.primopato.api.service.PatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("pato")
public class PatoController {

    private final MissaoInfoService missaoInfoService;
    private final PatoService patoService;

    @Operation(summary = "Endpoint para cadastro de patos primordiais")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pato cadastrado com sucesso")
    })
    @PostMapping("/cadastrar")
    public ResponseEntity<PatoResponse> cadastrarPato(Authentication authentication, @RequestBody PatoRequest patoRequest) {
        return ResponseEntity.ok(patoService.cadastrar(patoRequest, authentication.getName()));
    }

    @Operation(summary = "Endpoint para edição de patos primordiais")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pato editado com sucesso")
    })
    @PutMapping("/editar/{id}")
    public ResponseEntity<PatoResponse> editarPato(Authentication authentication, @PathVariable Long id, @RequestBody PatoRequest patoRequest) {
        Pato pato = patoService.editar(id, patoRequest, authentication.getName());
        missaoInfoService.atualizarMissaoInfo(pato);
        return ResponseEntity.ok(new PatoResponse(pato));
    }

    @Operation(summary = "Endpoint para apagar patos primordiais")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pato apagado com sucesso")
    })
    @DeleteMapping("/apagar/{id}")
    public ResponseEntity<?> apagarPato(Authentication authentication, @PathVariable Long id) {
        patoService.apagar(id, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Endpoint para buscar pato primordial por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pato buscado com sucesso")
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<PatoRequest> buscarPorId(Authentication authentication, @PathVariable Long id) {
        return ResponseEntity.ok(patoService.buscarPorId(id, authentication.getName()));
    }

    @Operation(summary = "Endpoint para buscar patos primordiais")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patos buscados com sucesso")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<PatoResponse>> buscarTodosFiltrado(Authentication authentication, @RequestParam(required = false, defaultValue = "") String filtro) {
        return ResponseEntity.ok(patoService.buscarTodosFiltrado(filtro, authentication.getName()));
    }

    @Operation(summary = "Endpoint para carregar estados de hibernação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estados de hibernação carregados com sucesso")
    })
    @GetMapping("/estadohibernacao/carregar")
    public ResponseEntity<List<DropDownResponse>> carregarEstadosHibernacao() {
        return ResponseEntity.ok(patoService.carregarEstadosHibernacao());
    }

    @Operation(summary = "Endpoint para captura de patos primordiais")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pato editado com sucesso")
    })
    @PutMapping("/capturar/{id}")
    public ResponseEntity<PatoResponse> capturarPato(Authentication authentication, @PathVariable Long id) {
        return ResponseEntity.ok(patoService.capturar(id, authentication.getName()));
    }

}
