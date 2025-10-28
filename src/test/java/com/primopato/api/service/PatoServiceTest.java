package com.primopato.api.service;

import com.primopato.api.entity.Pais;
import com.primopato.api.entity.Pato;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.enumerated.TipoSuperPoder;
import com.primopato.api.record.PatoContadorResponse;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.repository.MissaoInfoRepository;
import com.primopato.api.repository.PatoRepository;
import com.primopato.api.service.assembler.PatoAssembler;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatoServiceTest {

    @Test
    void testCadastrar() {
        PatoRepository patoRepository = mock(PatoRepository.class);
        MissaoInfoRepository missaoInfoRepository = mock(MissaoInfoRepository.class);
        PatoAssembler patoAssembler = mock(PatoAssembler.class);
        DroneService droneService = mock(DroneService.class);
        LocalizacaoService localizacaoService = mock(LocalizacaoService.class);
        PaisService paisService = mock(PaisService.class);
        SuperPoderService superPoderService = mock(SuperPoderService.class);
        UsuarioService usuarioService = mock(UsuarioService.class);
        PatoService service = new PatoService(patoRepository, missaoInfoRepository, patoAssembler, droneService, localizacaoService, paisService, superPoderService, usuarioService);

        PatoRequest request = new PatoRequest("SN", "M", "F", "P", 1f, 1f, 1, 1, "Brasil", "SP", "Campinas", "", "", 1f, EstadoHibernacao.DESPERTO, 100, 0, "Força", TipoSuperPoder.ELETRICIDADE, false);
        Pais pais = new Pais("Brasil");
        Pato pato = new Pato();

        when(paisService.obterOuCriarPais("P")).thenReturn(pais);
        when(patoAssembler.montarPato(any(), any(), any(), any(), any(), any())).thenReturn(pato);
        when(patoRepository.save(pato)).thenReturn(pato);

        Pato result = service.cadastrar(request, "user");

        assertSame(pato, result);
        verify(paisService, atLeastOnce()).obterOuCriarPais("P");
        verify(patoRepository).save(pato);
    }

    @Test
    void testEditar() {
        PatoRepository patoRepository = mock(PatoRepository.class);
        MissaoInfoRepository missaoInfoRepository = mock(MissaoInfoRepository.class);
        PatoAssembler patoAssembler = mock(PatoAssembler.class);
        DroneService droneService = mock(DroneService.class);
        LocalizacaoService localizacaoService = mock(LocalizacaoService.class);
        PaisService paisService = mock(PaisService.class);
        SuperPoderService superPoderService = mock(SuperPoderService.class);
        UsuarioService usuarioService = mock(UsuarioService.class);
        PatoService service = new PatoService(patoRepository, missaoInfoRepository, patoAssembler, droneService, localizacaoService, paisService, superPoderService, usuarioService);

        PatoRequest request = new PatoRequest("SN", "M", "F", "P", 1f, 1f, 1, 1, "Brasil", "SP", "Campinas", "", "", 1f, EstadoHibernacao.DESPERTO, 100, 0, "Força", TipoSuperPoder.ELETRICIDADE, false);
        Pais pais = new Pais("Brasil");
        Pato pato = new Pato();

        when(paisService.obterOuCriarPais("P")).thenReturn(pais);
        when(patoRepository.findByIdAndUsuario_Usuario(1L, "user")).thenReturn(Optional.of(pato));
        when(patoAssembler.editarPato(any(), any(), any(), any(), any(), any())).thenReturn(pato);
        when(patoRepository.save(pato)).thenReturn(pato);

        Pato result = service.editar(1L, request, "user");

        assertSame(pato, result);
        verify(patoRepository).save(pato);
    }

    @Test
    void testTemSuperPoderTrueFalse() {
        PatoRepository patoRepository = mock(PatoRepository.class);
        MissaoInfoRepository missaoInfoRepository = mock(MissaoInfoRepository.class);
        PatoAssembler patoAssembler = mock(PatoAssembler.class);
        DroneService droneService = mock(DroneService.class);
        LocalizacaoService localizacaoService = mock(LocalizacaoService.class);
        PaisService paisService = mock(PaisService.class);
        SuperPoderService superPoderService = mock(SuperPoderService.class);
        UsuarioService usuarioService = mock(UsuarioService.class);
        PatoService service = new PatoService(patoRepository, missaoInfoRepository, patoAssembler, droneService, localizacaoService, paisService, superPoderService, usuarioService);

        PatoRequest req1 = new PatoRequest("SN","M","F","P",1f,1f,1,1,"","","","", "",1f, EstadoHibernacao.DESPERTO,100,0,"Nome",TipoSuperPoder.ELETRICIDADE,false);
        PatoRequest req2 = new PatoRequest("SN","M","F","P",1f,1f,1,1,"","","","", "",1f, EstadoHibernacao.HIBERNACAO_PROFUNDA,100,0,null,null,false);

        try (MockedStatic<StringUtils> mocked = mockStatic(StringUtils.class)) {
            mocked.when(() -> StringUtils.isBlank(any())).thenReturn(false);
            boolean r1 = invokeTemSuperPoder(service, req1);
            mocked.when(() -> StringUtils.isBlank(any())).thenReturn(true);
            boolean r2 = invokeTemSuperPoder(service, req2);
            assertTrue(r1);
            assertFalse(r2);
        }
    }

    private boolean invokeTemSuperPoder(PatoService s, PatoRequest r) {
        try {
            var m = PatoService.class.getDeclaredMethod("temSuperPoder", PatoRequest.class);
            m.setAccessible(true);
            return (boolean) m.invoke(s, r);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testApagar() {
        PatoRepository patoRepository = mock(PatoRepository.class);
        MissaoInfoRepository missaoInfoRepository = mock(MissaoInfoRepository.class);
        PatoAssembler patoAssembler = mock(PatoAssembler.class);
        DroneService droneService = mock(DroneService.class);
        LocalizacaoService localizacaoService = mock(LocalizacaoService.class);
        PaisService paisService = mock(PaisService.class);
        SuperPoderService superPoderService = mock(SuperPoderService.class);
        UsuarioService usuarioService = mock(UsuarioService.class);
        PatoService service = new PatoService(patoRepository, missaoInfoRepository, patoAssembler, droneService, localizacaoService, paisService, superPoderService, usuarioService);

        Pato pato = new Pato();
        when(patoRepository.findByIdAndUsuario_Usuario(1L, "user")).thenReturn(Optional.of(pato));

        service.apagar(1L, "user");

        verify(patoRepository).delete(pato);
    }

    @Test
    void testGetPato_NotFound() {
        PatoRepository patoRepository = mock(PatoRepository.class);
        MissaoInfoRepository missaoInfoRepository = mock(MissaoInfoRepository.class);
        PatoAssembler patoAssembler = mock(PatoAssembler.class);
        DroneService droneService = mock(DroneService.class);
        LocalizacaoService localizacaoService = mock(LocalizacaoService.class);
        PaisService paisService = mock(PaisService.class);
        SuperPoderService superPoderService = mock(SuperPoderService.class);
        UsuarioService usuarioService = mock(UsuarioService.class);
        PatoService service = new PatoService(patoRepository, missaoInfoRepository, patoAssembler, droneService, localizacaoService, paisService, superPoderService, usuarioService);

        when(patoRepository.findByIdAndUsuario_Usuario(1L, "user")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getPato(1L, "user"));
    }

    @Test
    void testBuscarTodosFiltrado() {
        PatoRepository patoRepository = mock(PatoRepository.class);
        MissaoInfoRepository missaoInfoRepository = mock(MissaoInfoRepository.class);
        PatoAssembler patoAssembler = mock(PatoAssembler.class);
        DroneService droneService = mock(DroneService.class);
        LocalizacaoService localizacaoService = mock(LocalizacaoService.class);
        PaisService paisService = mock(PaisService.class);
        SuperPoderService superPoderService = mock(SuperPoderService.class);
        UsuarioService usuarioService = mock(UsuarioService.class);
        PatoService service = new PatoService(patoRepository, missaoInfoRepository, patoAssembler, droneService, localizacaoService, paisService, superPoderService, usuarioService);

        List<Pato> lista = List.of(new Pato());
        when(patoRepository.findAllByFiltro("%ABC%", true, false, "user")).thenReturn(lista);

        List<Pato> r = service.buscarTodosFiltrado("abc", null, "user");

        assertEquals(1, r.size());
    }

    @Test
    void testCapturar() {
        PatoRepository patoRepository = mock(PatoRepository.class);
        MissaoInfoRepository missaoInfoRepository = mock(MissaoInfoRepository.class);
        PatoAssembler patoAssembler = mock(PatoAssembler.class);
        DroneService droneService = mock(DroneService.class);
        LocalizacaoService localizacaoService = mock(LocalizacaoService.class);
        PaisService paisService = mock(PaisService.class);
        SuperPoderService superPoderService = mock(SuperPoderService.class);
        UsuarioService usuarioService = mock(UsuarioService.class);
        PatoService service = new PatoService(patoRepository, missaoInfoRepository, patoAssembler, droneService, localizacaoService, paisService, superPoderService, usuarioService);

        Pato pato = new Pato();
        when(patoRepository.findByIdAndUsuario_Usuario(1L, "user")).thenReturn(Optional.of(pato));
        when(patoRepository.save(pato)).thenReturn(pato);

        Pato result = service.capturar(1L, "user");

        assertTrue(result.isCapturado());
        verify(patoRepository).save(pato);
    }

    @Test
    void testBuscarQuantidadePatosCapturadosENaoCapturados() {
        PatoRepository patoRepository = mock(PatoRepository.class);
        MissaoInfoRepository missaoInfoRepository = mock(MissaoInfoRepository.class);
        PatoAssembler patoAssembler = mock(PatoAssembler.class);
        DroneService droneService = mock(DroneService.class);
        LocalizacaoService localizacaoService = mock(LocalizacaoService.class);
        PaisService paisService = mock(PaisService.class);
        SuperPoderService superPoderService = mock(SuperPoderService.class);
        UsuarioService usuarioService = mock(UsuarioService.class);
        PatoService service = new PatoService(patoRepository, missaoInfoRepository, patoAssembler, droneService, localizacaoService, paisService, superPoderService, usuarioService);

        when(patoRepository.contarPatosCapturados("user")).thenReturn(10L);
        when(patoRepository.contarPatosNaoCapturados("user")).thenReturn(5L);
        when(missaoInfoRepository.avgGanhoCientificoPorUsuario("user")).thenReturn(2.5f);
        when(missaoInfoRepository.avgGanhoParanormalPorUsuario("user")).thenReturn(3.5f);

        PatoContadorResponse r = service.buscarQuantidadePatosCapturadosENaoCapturados("user");

        assertEquals(10L, r.quantidadeCapturado());
        assertEquals(5L, r.quantidadeNaoCapturado());
        assertEquals(2.5f, r.porcentagemGanhoCientifico());
        assertEquals(3.5f, r.porcentagemGanhoParanormal());
    }
}
