package com.primopato.api.controller;

import com.primopato.api.entity.*;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.record.DropDownResponse;
import com.primopato.api.record.PatoContadorResponse;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.record.PatoResponse;
import com.primopato.api.service.MissaoInfoService;
import com.primopato.api.service.PatoService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatoControllerTest {

    @Test
    void testCadastrarPato() {
        MissaoInfoService missaoInfoService = mock(MissaoInfoService.class);
        PatoService patoService = mock(PatoService.class);
        PatoController controller = new PatoController(missaoInfoService, patoService);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");
        Pato pato = getPato();
        when(patoService.cadastrar(any(), eq("user"))).thenReturn(pato);

        PatoRequest request = mock(PatoRequest.class);

        ResponseEntity<PatoResponse> response = controller.cadastrarPato(auth, request);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(5L, response.getBody().id());
        verify(missaoInfoService).obterOuCriarMissaoInfo(5L, "user");
    }

    @Test
    void testEditarPato() {
        MissaoInfoService missaoInfoService = mock(MissaoInfoService.class);
        PatoService patoService = mock(PatoService.class);
        PatoController controller = new PatoController(missaoInfoService, patoService);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");

        Pato pato = getPato();
        pato.setId(2L);
        when(patoService.editar(eq(2L), any(), eq("user"))).thenReturn(pato);

        ResponseEntity<PatoResponse> response = controller.editarPato(auth, 2L, mock(PatoRequest.class));

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2L, response.getBody().id());
        verify(missaoInfoService).obterOuCriarMissaoInfo(2L, "user");
    }

    @Test
    void testApagarPato() {
        MissaoInfoService missaoInfoService = mock(MissaoInfoService.class);
        PatoService patoService = mock(PatoService.class);
        PatoController controller = new PatoController(missaoInfoService, patoService);
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");

        ResponseEntity<Void> response = controller.apagarPato(auth, 3L);

        assertEquals(204, response.getStatusCodeValue());
        verify(patoService).apagar(3L, "user");
    }

    @Test
    void testBuscarPorId() {
        MissaoInfoService missaoInfoService = mock(MissaoInfoService.class);
        PatoService patoService = mock(PatoService.class);
        PatoController controller = new PatoController(missaoInfoService, patoService);
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");

        Pato pato = getPato();
        when(patoService.getPato(5L, "user")).thenReturn(pato);

        ResponseEntity<PatoRequest> response = controller.buscarPorId(auth, 5L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(patoService).getPato(5L, "user");
    }

    private static Pato getPato() {
        Pais pais = new Pais("País");
        FabricanteDrone fabricanteDrone = new FabricanteDrone();
        ModeloDrone modeloDrone = new ModeloDrone();
        Drone drone = new Drone();
        fabricanteDrone.setPais(pais);
        modeloDrone.setFabricante(fabricanteDrone);
        drone.setModelo(modeloDrone);
        Coordenadas coordenadas = new Coordenadas(1d, 2d);
        Localizacao localizacao = new Localizacao();
        localizacao.setCoordenadas(coordenadas);
        Cidade cidade = new Cidade();
        Estado estado = new Estado();
        estado.setPais(pais);
        cidade.setEstado(estado);
        localizacao.setCidade(cidade);
        Pato pato = new Pato();
        pato.setId(5L);
        pato.setAltura(10f);
        pato.setPeso(10f);
        pato.setDroneQueEncontrou(drone);
        pato.setLocalizacao(localizacao);
        pato.setPrecisaoDoGpsQuandoEncontrado(1f);
        pato.setQuantidadeMutacoes(1);
        pato.setEstadoHibernacao(EstadoHibernacao.DESPERTO);
        return pato;
    }

    @Test
    void testBuscarTodosFiltrado() {
        MissaoInfoService missaoInfoService = mock(MissaoInfoService.class);
        PatoService patoService = mock(PatoService.class);
        PatoController controller = new PatoController(missaoInfoService, patoService);
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");

        Pato p1 = getPato();
        when(patoService.buscarTodosFiltrado("", null, "user")).thenReturn(List.of(p1));

        ResponseEntity<List<PatoResponse>> response = controller.buscarTodosFiltrado(auth, "", null);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(patoService).buscarTodosFiltrado("", null, "user");
    }

    @Test
    void testCapturarPato() {
        MissaoInfoService missaoInfoService = mock(MissaoInfoService.class);
        PatoService patoService = mock(PatoService.class);
        PatoController controller = new PatoController(missaoInfoService, patoService);
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");

        Pato p = getPato();
        p.setId(7L);
        when(patoService.capturar(7L, "user")).thenReturn(p);

        ResponseEntity<PatoResponse> response = controller.capturarPato(auth, 7L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(7L, response.getBody().id());
        verify(patoService).capturar(7L, "user");
    }

    @Test
    void testListarEstadosHibernacao() {
        MissaoInfoService missaoInfoService = mock(MissaoInfoService.class);
        PatoService patoService = mock(PatoService.class);
        PatoController controller = new PatoController(missaoInfoService, patoService);

        ResponseEntity<List<DropDownResponse>> response = controller.listarEstadosHibernacao();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(EstadoHibernacao.values().length, response.getBody().size());
    }

    @Test
    void testBuscarEstatisticas() {
        MissaoInfoService missaoInfoService = mock(MissaoInfoService.class);
        PatoService patoService = mock(PatoService.class);
        PatoController controller = new PatoController(missaoInfoService, patoService);
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");

        PatoContadorResponse contador = new PatoContadorResponse(10L, 5L, 2.5f, 1.5f);
        when(patoService.buscarQuantidadePatosCapturadosENaoCapturados("user")).thenReturn(contador);

        ResponseEntity<PatoContadorResponse> response = controller.buscarQuantidadePatosCapturadosENaoCapturados(auth);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(contador, response.getBody());
        verify(patoService).buscarQuantidadePatosCapturadosENaoCapturados("user");
    }
}
