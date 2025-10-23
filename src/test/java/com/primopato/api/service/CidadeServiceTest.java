package com.primopato.api.service;

import com.primopato.api.entity.Cidade;
import com.primopato.api.entity.Estado;
import com.primopato.api.entity.Pais;
import com.primopato.api.repository.CidadeRepository;
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
class CidadeServiceTest {

    @Mock
    private CidadeRepository cidadeRepository;

    @InjectMocks
    private CidadeService cidadeService;

    @Captor
    private ArgumentCaptor<Cidade> cidadeCaptor;

    private Estado estado;
    private List<Cidade> cidades;

    @BeforeEach
    void setUp() {
        Pais pais = new Pais();
        pais.setId(1L);
        pais.setNome("Brasil");

        estado = new Estado();
        estado.setId(1L);
        estado.setNome("São Paulo");
        estado.setPais(pais);

        cidades = new ArrayList<>();
        estado.setCidades(cidades);
    }

    @Test
    void deveRetornarCidadeExistenteQuandoEncontrada() {
        Cidade cidadeExistente = new Cidade("São Paulo", estado);
        cidadeExistente.setId(1L);
        cidades.add(cidadeExistente);

        Cidade resultado = cidadeService.obterOuCriarCidade("São Paulo", estado);

        assertNotNull(resultado);
        assertEquals(cidadeExistente, resultado);
        assertEquals("São Paulo", resultado.getNome());
        assertEquals(estado, resultado.getEstado());
        verify(cidadeRepository, never()).save(any(Cidade.class));
    }

    @Test
    void deveCriarNovaCidadeQuandoNaoEncontrada() {
        Cidade cidadeNova = new Cidade("Campinas", estado);
        cidadeNova.setId(2L);
        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidadeNova);

        Cidade resultado = cidadeService.obterOuCriarCidade("Campinas", estado);

        assertNotNull(resultado);
        assertEquals("Campinas", resultado.getNome());
        assertEquals(estado, resultado.getEstado());
        verify(cidadeRepository, times(1)).save(cidadeCaptor.capture());

        Cidade cidadeSalva = cidadeCaptor.getValue();
        assertEquals("Campinas", cidadeSalva.getNome());
        assertEquals(estado, cidadeSalva.getEstado());
    }

    @Test
    void deveFormatarNomeCidadeParaInicialMaiuscula() {
        Cidade cidadeExistente = new Cidade("São Paulo", estado);
        cidades.add(cidadeExistente);

        Cidade resultado = cidadeService.obterOuCriarCidade("são paulo", estado);

        assertNotNull(resultado);
        assertEquals(cidadeExistente, resultado);
        verify(cidadeRepository, never()).save(any(Cidade.class));
    }

    @Test
    void deveFormatarNomeCidadeAoCriarNova() {
        Cidade cidadeNova = new Cidade("Santos", estado);
        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidadeNova);

        Cidade resultado = cidadeService.obterOuCriarCidade("santos", estado);

        verify(cidadeRepository).save(cidadeCaptor.capture());
        Cidade cidadeSalva = cidadeCaptor.getValue();
        assertEquals("Santos", cidadeSalva.getNome());
    }

    @Test
    void deveCriarNovaCidadeQuandoNomeNaoCorresponderExatamente() {
        Cidade cidadeExistente = new Cidade("São Paulo", estado);
        cidades.add(cidadeExistente);

        Cidade cidadeNova = new Cidade("Campinas", estado);
        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidadeNova);

        Cidade resultado = cidadeService.obterOuCriarCidade("Campinas", estado);

        assertNotNull(resultado);
        assertNotEquals(cidadeExistente, resultado);
        verify(cidadeRepository, times(1)).save(any(Cidade.class));
    }

    @Test
    void deveRetornarPrimeiraCidadeQuandoHouverMultiplasComMesmoNome() {
        Cidade cidade1 = new Cidade("São Paulo", estado);
        cidade1.setId(1L);
        Cidade cidade2 = new Cidade("São Paulo", estado);
        cidade2.setId(2L);

        cidades.add(cidade1);
        cidades.add(cidade2);

        Cidade resultado = cidadeService.obterOuCriarCidade("São Paulo", estado);

        assertNotNull(resultado);
        assertEquals(cidade1, resultado);
        assertEquals(1L, resultado.getId());
        verify(cidadeRepository, never()).save(any(Cidade.class));
    }

    @Test
    void deveCriarNovaCidadeQuandoListaVazia() {
        assertTrue(cidades.isEmpty());
        Cidade cidadeNova = new Cidade("Campinas", estado);
        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidadeNova);

        Cidade resultado = cidadeService.obterOuCriarCidade("Campinas", estado);

        assertNotNull(resultado);
        assertEquals("Campinas", resultado.getNome());
        verify(cidadeRepository, times(1)).save(any(Cidade.class));
    }

    @Test
    void deveAssociarEstadoCorretamenteANovaCidade() {
        Cidade cidadeNova = new Cidade("Ribeirão Preto", estado);
        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidadeNova);

        Cidade resultado = cidadeService.obterOuCriarCidade("Ribeirão Preto", estado);

        verify(cidadeRepository).save(cidadeCaptor.capture());
        Cidade cidadeSalva = cidadeCaptor.getValue();
        assertNotNull(cidadeSalva.getEstado());
        assertEquals(estado, cidadeSalva.getEstado());
        assertEquals("São Paulo", cidadeSalva.getEstado().getNome());
        assertEquals("Brasil", cidadeSalva.getEstado().getPais().getNome());
    }

    @Test
    void deveTratarNomesComCaracteresEspeciais() {
        Cidade cidadeExistente = new Cidade("São José dos Campos", estado);
        cidades.add(cidadeExistente);

        Cidade resultado = cidadeService.obterOuCriarCidade("são josé dos campos", estado);

        assertNotNull(resultado);
        assertEquals(cidadeExistente, resultado);
        verify(cidadeRepository, never()).save(any(Cidade.class));
    }

    @Test
    void deveCriarCidadeComNomeFormatadoQuandoReceberEntradaMinusculas() {
        Cidade cidadeNova = new Cidade("Bauru", estado);
        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidadeNova);

        cidadeService.obterOuCriarCidade("bauru", estado);

        verify(cidadeRepository).save(cidadeCaptor.capture());
        Cidade cidadeSalva = cidadeCaptor.getValue();
        assertEquals("Bauru", cidadeSalva.getNome());
    }

    @Test
    void deveBuscarEmListaComVariasCidadesERetornarCorreta() {
        Cidade cidade1 = new Cidade("Campinas", estado);
        Cidade cidade2 = new Cidade("Santos", estado);
        Cidade cidade3 = new Cidade("Sorocaba", estado);

        cidades.add(cidade1);
        cidades.add(cidade2);
        cidades.add(cidade3);

        Cidade resultado = cidadeService.obterOuCriarCidade("Santos", estado);

        assertNotNull(resultado);
        assertEquals(cidade2, resultado);
        assertEquals("Santos", resultado.getNome());
        verify(cidadeRepository, never()).save(any(Cidade.class));
    }

    @Test
    void deveCriarCidadeQuandoBuscarPorNomeNaoExistenteEmListaPopulada() {
        Cidade cidade1 = new Cidade("Campinas", estado);
        Cidade cidade2 = new Cidade("Santos", estado);
        cidades.add(cidade1);
        cidades.add(cidade2);

        Cidade cidadeNova = new Cidade("Piracicaba", estado);
        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidadeNova);

        Cidade resultado = cidadeService.obterOuCriarCidade("Piracicaba", estado);

        assertNotNull(resultado);
        assertEquals("Piracicaba", resultado.getNome());
        verify(cidadeRepository, times(1)).save(any(Cidade.class));
    }
}