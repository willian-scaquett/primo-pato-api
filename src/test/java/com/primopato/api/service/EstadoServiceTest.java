package com.primopato.api.service;

import com.primopato.api.entity.Estado;
import com.primopato.api.entity.Pais;
import com.primopato.api.repository.EstadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstadoServiceTest {

    @Mock
    private EstadoRepository estadoRepository;

    @InjectMocks
    private EstadoService estadoService;

    @Captor
    private ArgumentCaptor<Estado> estadoCaptor;

    private Pais pais;
    private List<Estado> estados;

    @BeforeEach
    void setUp() {
        pais = new Pais();
        pais.setId(1L);
        pais.setNome("Brasil");

        estados = new ArrayList<>();
        pais.setEstados(estados);
    }

    @Test
    void deveRetornarEstadoExistenteQuandoEncontrado() {
        Estado estadoExistente = new Estado("São Paulo", pais);
        estadoExistente.setId(1L);
        estados.add(estadoExistente);

        Estado resultado = estadoService.obterOuCriarEstado("São Paulo", pais);

        assertNotNull(resultado);
        assertEquals(estadoExistente, resultado);
        assertEquals("São Paulo", resultado.getNome());
        assertEquals(pais, resultado.getPais());
        verify(estadoRepository, never()).save(any(Estado.class));
    }

    @Test
    void deveCriarNovoEstadoQuandoNaoEncontrado() {
        Estado estadoNovo = new Estado("Rio de Janeiro", pais);
        estadoNovo.setId(2L);
        when(estadoRepository.save(any(Estado.class))).thenReturn(estadoNovo);

        Estado resultado = estadoService.obterOuCriarEstado("Rio de Janeiro", pais);

        assertNotNull(resultado);
        assertEquals("Rio de Janeiro", resultado.getNome());
        assertEquals(pais, resultado.getPais());
        verify(estadoRepository, times(1)).save(estadoCaptor.capture());

        Estado estadoSalvo = estadoCaptor.getValue();
        assertEquals("Rio de Janeiro", estadoSalvo.getNome());
        assertEquals(pais, estadoSalvo.getPais());
    }

    @Test
    void deveFormatarNomeEstadoParaInicialMaiuscula() {
        Estado estadoExistente = new Estado("São Paulo", pais);
        estados.add(estadoExistente);

        Estado resultado = estadoService.obterOuCriarEstado("são paulo", pais);

        assertNotNull(resultado);
        assertEquals(estadoExistente, resultado);
        verify(estadoRepository, never()).save(any(Estado.class));
    }

    @Test
    void deveFormatarNomeEstadoAoCriarNovo() {
        Estado estadoNovo = new Estado("Minas Gerais", pais);
        when(estadoRepository.save(any(Estado.class))).thenReturn(estadoNovo);

        Estado resultado = estadoService.obterOuCriarEstado("minas gerais", pais);

        verify(estadoRepository).save(estadoCaptor.capture());
        Estado estadoSalvo = estadoCaptor.getValue();
        assertEquals("Minas Gerais", estadoSalvo.getNome());
    }

    @Test
    void deveCriarNovoEstadoQuandoNomeNaoCorresponderExatamente() {
        Estado estadoExistente = new Estado("São Paulo", pais);
        estados.add(estadoExistente);

        Estado estadoNovo = new Estado("Paraná", pais);
        when(estadoRepository.save(any(Estado.class))).thenReturn(estadoNovo);

        Estado resultado = estadoService.obterOuCriarEstado("Paraná", pais);

        assertNotNull(resultado);
        assertNotEquals(estadoExistente, resultado);
        verify(estadoRepository, times(1)).save(any(Estado.class));
    }

    @Test
    void deveRetornarPrimeiroEstadoQuandoHouverMultiplosComMesmoNome() {
        Estado estado1 = new Estado("São Paulo", pais);
        estado1.setId(1L);
        Estado estado2 = new Estado("São Paulo", pais);
        estado2.setId(2L);

        estados.add(estado1);
        estados.add(estado2);

        Estado resultado = estadoService.obterOuCriarEstado("São Paulo", pais);

        assertNotNull(resultado);
        assertEquals(estado1, resultado);
        assertEquals(1L, resultado.getId());
        verify(estadoRepository, never()).save(any(Estado.class));
    }

    @Test
    void deveCriarNovoEstadoQuandoListaEstiverVazia() {
        assertTrue(estados.isEmpty());
        Estado estadoNovo = new Estado("Bahia", pais);
        when(estadoRepository.save(any(Estado.class))).thenReturn(estadoNovo);

        Estado resultado = estadoService.obterOuCriarEstado("Bahia", pais);

        assertNotNull(resultado);
        assertEquals("Bahia", resultado.getNome());
        verify(estadoRepository, times(1)).save(any(Estado.class));
    }

    @Test
    void deveAssociarPaisCorretamenteAoNovoEstadoCriado() {
        Estado estadoNovo = new Estado("Santa Catarina", pais);
        when(estadoRepository.save(any(Estado.class))).thenReturn(estadoNovo);

        Estado resultado = estadoService.obterOuCriarEstado("Santa Catarina", pais);

        verify(estadoRepository).save(estadoCaptor.capture());
        Estado estadoSalvo = estadoCaptor.getValue();
        assertNotNull(estadoSalvo.getPais());
        assertEquals(pais, estadoSalvo.getPais());
        assertEquals("Brasil", estadoSalvo.getPais().getNome());
    }

    @Test
    void deveTratarNomesComCaracteresEspeciais() {
        Estado estadoExistente = new Estado("Espírito Santo", pais);
        estados.add(estadoExistente);

        Estado resultado = estadoService.obterOuCriarEstado("espírito santo", pais);

        assertNotNull(resultado);
        assertEquals(estadoExistente, resultado);
        verify(estadoRepository, never()).save(any(Estado.class));
    }

    @Test
    void deveCriarEstadoComNomeFormatadoQuandoReceberEntradaMinusculas() {
        Estado estadoNovo = new Estado("Ceará", pais);
        when(estadoRepository.save(any(Estado.class))).thenReturn(estadoNovo);

        estadoService.obterOuCriarEstado("ceará", pais);

        verify(estadoRepository).save(estadoCaptor.capture());
        Estado estadoSalvo = estadoCaptor.getValue();
        assertEquals("Ceará", estadoSalvo.getNome());
    }

    @Test
    void deveBuscarEmListaComVariosEstadosERetornarCorreto() {
        Estado estado1 = new Estado("São Paulo", pais);
        Estado estado2 = new Estado("Rio de Janeiro", pais);
        Estado estado3 = new Estado("Minas Gerais", pais);

        estados.add(estado1);
        estados.add(estado2);
        estados.add(estado3);

        Estado resultado = estadoService.obterOuCriarEstado("Rio de Janeiro", pais);

        assertNotNull(resultado);
        assertEquals(estado2, resultado);
        assertEquals("Rio de Janeiro", resultado.getNome());
        verify(estadoRepository, never()).save(any(Estado.class));
    }

    @Test
    void deveCriarEstadoQuandoBuscarPorNomeNaoExistenteEmListaPopulada() {
        Estado estado1 = new Estado("São Paulo", pais);
        Estado estado2 = new Estado("Rio De Janeiro", pais);
        estados.add(estado1);
        estados.add(estado2);

        Estado estadoNovo = new Estado("Goiás", pais);
        when(estadoRepository.save(any(Estado.class))).thenReturn(estadoNovo);

        Estado resultado = estadoService.obterOuCriarEstado("Goiás", pais);

        assertNotNull(resultado);
        assertEquals("Goiás", resultado.getNome());
        verify(estadoRepository, times(1)).save(any(Estado.class));
    }
}