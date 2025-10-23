package com.primopato.api.service;

import com.primopato.api.entity.SuperPoder;
import com.primopato.api.enumerated.TipoSuperPoder;
import com.primopato.api.repository.SuperPoderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuperPoderServiceTest {

    @Mock
    private SuperPoderRepository superPoderRepository;

    @InjectMocks
    private SuperPoderService superPoderService;

    @Captor
    private ArgumentCaptor<SuperPoder> superPoderCaptor;

    private SuperPoder superPoder;

    @BeforeEach
    void setUp() {
        superPoder = new SuperPoder("Invisibilidade", TipoSuperPoder.SOBRENATURAL);
        superPoder.setId(1L);
    }

    @Test
    void deveCarregarSuperPoderesPorTipo() {
        SuperPoder superPoder2 = new SuperPoder("Telepatia", TipoSuperPoder.PSIQUICO);
        List<SuperPoder> superPoderes = Arrays.asList(superPoder, superPoder2);

        when(superPoderRepository.findAllByTipo(TipoSuperPoder.PSIQUICO))
                .thenReturn(superPoderes);

        List<SuperPoder> resultado = superPoderService.carregarSuperPoderes(TipoSuperPoder.PSIQUICO);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertThat(resultado).contains(superPoder, superPoder2);
        verify(superPoderRepository).findAllByTipo(TipoSuperPoder.PSIQUICO);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverSuperPoderes() {
        when(superPoderRepository.findAllByTipo(TipoSuperPoder.VELOCIDADE))
                .thenReturn(List.of());

        List<SuperPoder> resultado = superPoderService.carregarSuperPoderes(TipoSuperPoder.VELOCIDADE);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(superPoderRepository).findAllByTipo(TipoSuperPoder.VELOCIDADE);
    }

    @Test
    void deveCarregarSuperPoderesDeTiposDiferentes() {
        SuperPoder poder1 = new SuperPoder("Voo Super Rápido", TipoSuperPoder.VELOCIDADE);
        SuperPoder poder2 = new SuperPoder("Bola De Fogo", TipoSuperPoder.FOGO);

        when(superPoderRepository.findAllByTipo(TipoSuperPoder.VELOCIDADE))
                .thenReturn(List.of(poder1));
        when(superPoderRepository.findAllByTipo(TipoSuperPoder.FOGO))
                .thenReturn(List.of(poder2));

        List<SuperPoder> velocidade = superPoderService.carregarSuperPoderes(TipoSuperPoder.VELOCIDADE);
        List<SuperPoder> fogo = superPoderService.carregarSuperPoderes(TipoSuperPoder.FOGO);

        assertEquals(1, velocidade.size());
        assertEquals(1, fogo.size());
        assertEquals("Voo Super Rápido", velocidade.getFirst().getNome());
        assertEquals("Bola De Fogo", fogo.getFirst().getNome());
    }

    @Test
    void deveRetornarSuperPoderExistenteQuandoEncontrado() {
        when(superPoderRepository.findByNomeAndTipo("Invisibilidade", TipoSuperPoder.SOBRENATURAL))
                .thenReturn(Optional.of(superPoder));

        SuperPoder resultado = superPoderService.obterOuCriarSuperPoder("Invisibilidade", TipoSuperPoder.SOBRENATURAL);

        assertNotNull(resultado);
        assertEquals(superPoder, resultado);
        assertEquals("Invisibilidade", resultado.getNome());
        assertEquals(TipoSuperPoder.SOBRENATURAL, resultado.getTipo());
        verify(superPoderRepository, never()).save(any(SuperPoder.class));
    }

    @Test
    void deveCriarNovoSuperPoderQuandoNaoEncontrado() {
        when(superPoderRepository.findByNomeAndTipo("Teleporte", TipoSuperPoder.TELETRANSPORTE))
                .thenReturn(Optional.empty());

        SuperPoder novoSuperPoder = new SuperPoder("Teleporte", TipoSuperPoder.TELETRANSPORTE);
        when(superPoderRepository.save(any(SuperPoder.class))).thenReturn(novoSuperPoder);

        SuperPoder resultado = superPoderService.obterOuCriarSuperPoder("Teleporte", TipoSuperPoder.TELETRANSPORTE);

        assertNotNull(resultado);
        assertEquals("Teleporte", resultado.getNome());
        assertEquals(TipoSuperPoder.TELETRANSPORTE, resultado.getTipo());
        verify(superPoderRepository, times(1)).save(superPoderCaptor.capture());

        SuperPoder salvo = superPoderCaptor.getValue();
        assertEquals("Teleporte", salvo.getNome());
        assertEquals(TipoSuperPoder.TELETRANSPORTE, salvo.getTipo());
    }

    @Test
    void deveFormatarNomeSuperPoderParaInicialMaiuscula() {
        when(superPoderRepository.findByNomeAndTipo("invisibilidade", TipoSuperPoder.SOBRENATURAL))
                .thenReturn(Optional.empty());

        SuperPoder novoSuperPoder = new SuperPoder("Invisibilidade", TipoSuperPoder.SOBRENATURAL);
        when(superPoderRepository.save(any(SuperPoder.class))).thenReturn(novoSuperPoder);

        superPoderService.obterOuCriarSuperPoder("invisibilidade", TipoSuperPoder.SOBRENATURAL);

        verify(superPoderRepository).save(superPoderCaptor.capture());

        SuperPoder salvo = superPoderCaptor.getValue();
        assertEquals("Invisibilidade", salvo.getNome());
    }

    @Test
    void deveBuscarSuperPoderComNomeOriginalSemFormatacao() {
        when(superPoderRepository.findByNomeAndTipo("teleporte", TipoSuperPoder.TELETRANSPORTE))
                .thenReturn(Optional.of(superPoder));

        superPoderService.obterOuCriarSuperPoder("teleporte", TipoSuperPoder.TELETRANSPORTE);

        verify(superPoderRepository).findByNomeAndTipo("teleporte", TipoSuperPoder.TELETRANSPORTE);
    }

    @Test
    void deveAssociarTipoAoCriarSuperPoder() {
        when(superPoderRepository.findByNomeAndTipo("Hidrocinese", TipoSuperPoder.AGUA))
                .thenReturn(Optional.empty());

        SuperPoder novoSuperPoder = new SuperPoder("Hidrocinese", TipoSuperPoder.AGUA);
        when(superPoderRepository.save(any(SuperPoder.class))).thenReturn(novoSuperPoder);

        superPoderService.obterOuCriarSuperPoder("Hidrocinese", TipoSuperPoder.AGUA);

        verify(superPoderRepository).save(superPoderCaptor.capture());

        SuperPoder salvo = superPoderCaptor.getValue();
        assertEquals(TipoSuperPoder.AGUA, salvo.getTipo());
    }

    @Test
    void deveCriarSuperPoderesComMesmoNomeMasTiposDiferentes() {
        SuperPoder poder1 = new SuperPoder("Controle", TipoSuperPoder.AGUA);
        SuperPoder poder2 = new SuperPoder("Controle", TipoSuperPoder.FOGO);

        when(superPoderRepository.findByNomeAndTipo("Controle", TipoSuperPoder.AGUA))
                .thenReturn(Optional.empty());
        when(superPoderRepository.findByNomeAndTipo("Controle", TipoSuperPoder.FOGO))
                .thenReturn(Optional.empty());

        when(superPoderRepository.save(any(SuperPoder.class)))
                .thenReturn(poder1)
                .thenReturn(poder2);

        SuperPoder resultado1 = superPoderService.obterOuCriarSuperPoder("Controle", TipoSuperPoder.AGUA);
        SuperPoder resultado2 = superPoderService.obterOuCriarSuperPoder("Controle", TipoSuperPoder.FOGO);

        assertNotNull(resultado1);
        assertNotNull(resultado2);
        assertEquals("Controle", resultado1.getNome());
        assertEquals("Controle", resultado2.getNome());
        assertEquals(TipoSuperPoder.AGUA, resultado1.getTipo());
        assertEquals(TipoSuperPoder.FOGO, resultado2.getTipo());
        verify(superPoderRepository, times(2)).save(any(SuperPoder.class));
    }

    @Test
    void deveTratarNomesComCaracteresEspeciais() {
        SuperPoder poderComAcento = new SuperPoder("Choque Elétrico", TipoSuperPoder.ELETRICIDADE);
        when(superPoderRepository.findByNomeAndTipo("choque elétrico", TipoSuperPoder.ELETRICIDADE))
                .thenReturn(Optional.empty());
        when(superPoderRepository.save(any(SuperPoder.class))).thenReturn(poderComAcento);

        superPoderService.obterOuCriarSuperPoder("choque elétrico", TipoSuperPoder.ELETRICIDADE);

        verify(superPoderRepository).save(superPoderCaptor.capture());

        SuperPoder salvo = superPoderCaptor.getValue();
        assertEquals("Choque Elétrico", salvo.getNome());
    }

    @Test
    void deveRetornarSuperPoderSemCriarDuplicado() {
        when(superPoderRepository.findByNomeAndTipo("Invisibilidade", TipoSuperPoder.SOBRENATURAL))
                .thenReturn(Optional.of(superPoder));

        SuperPoder resultado1 = superPoderService.obterOuCriarSuperPoder("Invisibilidade", TipoSuperPoder.SOBRENATURAL);
        SuperPoder resultado2 = superPoderService.obterOuCriarSuperPoder("Invisibilidade", TipoSuperPoder.SOBRENATURAL);

        assertEquals(resultado1, resultado2);
        verify(superPoderRepository, times(2)).findByNomeAndTipo("Invisibilidade", TipoSuperPoder.SOBRENATURAL);
        verify(superPoderRepository, never()).save(any(SuperPoder.class));
    }

    @Test
    void deveCriarSuperPoderComConstrutorCorreto() {
        when(superPoderRepository.findByNomeAndTipo("Piromanipulação", TipoSuperPoder.FOGO))
                .thenReturn(Optional.empty());

        SuperPoder novoSuperPoder = new SuperPoder("Piromanipulação", TipoSuperPoder.FOGO);
        novoSuperPoder.setId(5L);
        when(superPoderRepository.save(any(SuperPoder.class))).thenReturn(novoSuperPoder);

        SuperPoder resultado = superPoderService.obterOuCriarSuperPoder("Piromanipulação", TipoSuperPoder.FOGO);

        assertNotNull(resultado);
        assertEquals(5L, resultado.getId());
        assertEquals("Piromanipulação", resultado.getNome());
        assertEquals(TipoSuperPoder.FOGO, resultado.getTipo());
        verify(superPoderRepository).save(any(SuperPoder.class));
    }

    @Test
    void deveCarregarMultiplosSuperPoderesMesmoTipo() {
        SuperPoder poder1 = new SuperPoder("Voo", TipoSuperPoder.VELOCIDADE);
        SuperPoder poder2 = new SuperPoder("Super Velocidade", TipoSuperPoder.VELOCIDADE);
        SuperPoder poder3 = new SuperPoder("Reflexos Rápidos", TipoSuperPoder.VELOCIDADE);

        List<SuperPoder> poderes = Arrays.asList(poder1, poder2, poder3);
        when(superPoderRepository.findAllByTipo(TipoSuperPoder.VELOCIDADE))
                .thenReturn(poderes);

        List<SuperPoder> resultado = superPoderService.carregarSuperPoderes(TipoSuperPoder.VELOCIDADE);

        assertEquals(3, resultado.size());
        assertThat(resultado).contains(poder1, poder2, poder3);
    }
}