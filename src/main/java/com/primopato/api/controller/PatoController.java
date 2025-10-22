package com.primopato.api.controller;

import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.record.DropDownResponse;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.record.PatoResponse;
import com.primopato.api.service.PatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("pato")
public class PatoController {

    private final PatoService patoService;

    @Operation(summary = "Endpoint para cadastro de pato")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pato cadastrado com sucesso")
    })
    @PostMapping("/cadastrar")
    public ResponseEntity<PatoResponse> cadastrarPato(Authentication authentication, @Valid @RequestBody PatoRequest patoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new PatoResponse(patoService.cadastrar(patoRequest, authentication.getName())));
    }

    @Operation(summary = "Endpoint para edição de pato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pato editado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pato não encontrado para o usuário")
    })
    @PutMapping("/editar/{id}")
    public ResponseEntity<PatoResponse> editarPato(Authentication authentication, @PathVariable Long id, @Valid @RequestBody PatoRequest patoRequest) {
        return ResponseEntity.ok(new PatoResponse(patoService.editar(id, patoRequest, authentication.getName())));
    }

    @Operation(summary = "Endpoint para apagar pato")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pato apagado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pato não encontrado para o usuário")
    })
    @DeleteMapping("/apagar/{id}")
    public ResponseEntity<Void> apagarPato(Authentication authentication, @PathVariable Long id) {
        patoService.apagar(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Endpoint para buscar pato por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pato buscado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pato não encontrado para o usuário")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatoRequest> buscarPorId(Authentication authentication, @PathVariable Long id) {
        return ResponseEntity.ok(new PatoRequest(patoService.getPato(id, authentication.getName())));
    }

    @Operation(summary = "Endpoint para buscar patos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patos buscados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<PatoResponse>> buscarTodosFiltrado(Authentication authentication, @RequestParam(required = false, defaultValue = "") String filtro) {
        return ResponseEntity.ok(
                patoService.buscarTodosFiltrado(filtro, authentication.getName())
                        .stream()
                        .map(PatoResponse::new)
                        .toList()
        );
    }

    @Operation(summary = "Endpoint para definir pato como capturado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pato editado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pato não encontrado para o usuário")
    })
    @PutMapping("/capturar/{id}")
    public ResponseEntity<PatoResponse> capturarPato(Authentication authentication, @PathVariable Long id) {
        return ResponseEntity.ok(new PatoResponse(patoService.capturar(id, authentication.getName())));
    }

    @Operation(summary = "Endpoint para listar estados de hibernação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estados de hibernação carregados com sucesso")
    })
    @GetMapping("/estadohibernacao")
    public ResponseEntity<List<DropDownResponse>> listarEstadosHibernacao() {
        return ResponseEntity.ok(
                Stream.of(EstadoHibernacao.values())
                .map(e -> new DropDownResponse(e.name(), e.getNome()))
                .toList()
        );
    }
}
