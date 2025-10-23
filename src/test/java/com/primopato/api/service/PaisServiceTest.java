package com.primopato.api.service;

import com.primopato.api.entity.Pais;
import com.primopato.api.repository.PaisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaisServiceTest {

    @Mock
    private PaisRepository paisRepository;

    @InjectMocks
    private PaisService paisService;

    @Captor
    private ArgumentCaptor<Pais> paisCaptor;

    private Pais pais;

    @BeforeEach
    void setUp() {
        pais = new Pais();
        pais.setId(1L);
        pais.setNome("Brasil");
    }

    @Test
    void deveRetornarPaisExistenteQuandoEncontrado() {
        when(paisRepository.findByNome("Brasil")).thenReturn(Optional.of(pais));

        Pais resultado = paisService.obterOuCriarPais("Brasil");

        assertNotNull(resultado);
        assertEquals(pais, resultado);
        assertEquals("Brasil", resultado.getNome());
        assertEquals(1L, resultado.getId());
        verify(paisRepository).findByNome("Brasil");
        verify(paisRepository, never()).save(any(Pais.class));
    }

    @Test
    void deveCriarNovoPaisQuandoNaoEncontrado() {
        when(paisRepository.findByNome("Argentina")).thenReturn(Optional.empty());

        Pais paisNovo = new Pais();
        paisNovo.setId(2L);
        paisNovo.setNome("Argentina");
        when(paisRepository.save(any(Pais.class))).thenReturn(paisNovo);

        Pais resultado = paisService.obterOuCriarPais("Argentina");

        assertNotNull(resultado);
        assertEquals("Argentina", resultado.getNome());
        assertEquals(2L, resultado.getId());
        verify(paisRepository).findByNome("Argentina");
        verify(paisRepository, times(1)).save(paisCaptor.capture());

        Pais paisSalvo = paisCaptor.getValue();
        assertEquals("Argentina", paisSalvo.getNome());
    }

    @Test
    void deveFormatarNomePaisParaInicialMaiuscula() {
        when(paisRepository.findByNome("Brasil")).thenReturn(Optional.of(pais));

        Pais resultado = paisService.obterOuCriarPais("brasil");

        assertNotNull(resultado);
        assertEquals(pais, resultado);
        verify(paisRepository).findByNome("Brasil");
        verify(paisRepository, never()).save(any(Pais.class));
    }

    @Test
    void deveFormatarNomePaisAoCriarNovo() {
        when(paisRepository.findByNome("Chile")).thenReturn(Optional.empty());

        Pais paisNovo = new Pais();
        paisNovo.setNome("Chile");
        when(paisRepository.save(any(Pais.class))).thenReturn(paisNovo);

        paisService.obterOuCriarPais("chile");

        verify(paisRepository).findByNome("Chile");
        verify(paisRepository).save(paisCaptor.capture());

        Pais paisSalvo = paisCaptor.getValue();
        assertEquals("Chile", paisSalvo.getNome());
    }

    @Test
    void deveTratarNomesComCaracteresEspeciais() {
        Pais paisComAcento = new Pais();
        paisComAcento.setNome("México");
        when(paisRepository.findByNome("México")).thenReturn(Optional.of(paisComAcento));

        Pais resultado = paisService.obterOuCriarPais("méxico");

        assertNotNull(resultado);
        assertEquals(paisComAcento, resultado);
        verify(paisRepository).findByNome("México");
        verify(paisRepository, never()).save(any(Pais.class));
    }

    @Test
    void deveCriarPaisComNomeFormatadoCorreto() {
        when(paisRepository.findByNome("Uruguai")).thenReturn(Optional.empty());

        Pais paisNovo = new Pais();
        paisNovo.setNome("Uruguai");
        when(paisRepository.save(any(Pais.class))).thenReturn(paisNovo);

        paisService.obterOuCriarPais("URUGUAI");

        verify(paisRepository).save(paisCaptor.capture());

        Pais paisSalvo = paisCaptor.getValue();
        assertEquals("Uruguai", paisSalvo.getNome());
    }

    @Test
    void deveBuscarPaisComNomeExato() {
        when(paisRepository.findByNome("Brasil")).thenReturn(Optional.of(pais));

        Pais resultado = paisService.obterOuCriarPais("Brasil");

        assertEquals("Brasil", resultado.getNome());
        verify(paisRepository).findByNome("Brasil");
    }

    @Test
    void deveCriarPaisesComNomesDiferentes() {
        when(paisRepository.findByNome("Paraguai")).thenReturn(Optional.empty());
        when(paisRepository.findByNome("Bolívia")).thenReturn(Optional.empty());

        Pais pais1 = new Pais();
        pais1.setNome("Paraguai");
        Pais pais2 = new Pais();
        pais2.setNome("Bolívia");

        when(paisRepository.save(any(Pais.class)))
                .thenReturn(pais1)
                .thenReturn(pais2);

        Pais resultado1 = paisService.obterOuCriarPais("paraguai");
        Pais resultado2 = paisService.obterOuCriarPais("bolívia");

        assertNotNull(resultado1);
        assertNotNull(resultado2);
        assertEquals("Paraguai", resultado1.getNome());
        assertEquals("Bolívia", resultado2.getNome());
        verify(paisRepository, times(2)).save(any(Pais.class));
    }

    @Test
    void deveRetornarPaisSemCriarDuplicado() {
        when(paisRepository.findByNome("Brasil")).thenReturn(Optional.of(pais));

        Pais resultado1 = paisService.obterOuCriarPais("Brasil");
        Pais resultado2 = paisService.obterOuCriarPais("brasil");

        assertEquals(resultado1, resultado2);
        verify(paisRepository, times(2)).findByNome("Brasil");
        verify(paisRepository, never()).save(any(Pais.class));
    }

    @Test
    void deveCriarNovoPaisComConstrutorCorreto() {
        when(paisRepository.findByNome("Colômbia")).thenReturn(Optional.empty());

        Pais paisNovo = new Pais();
        paisNovo.setNome("Colômbia");
        when(paisRepository.save(any(Pais.class))).thenReturn(paisNovo);

        paisService.obterOuCriarPais("colômbia");

        verify(paisRepository).save(paisCaptor.capture());

        Pais paisSalvo = paisCaptor.getValue();
        assertNotNull(paisSalvo);
        assertEquals("Colômbia", paisSalvo.getNome());
    }

    @Test
    void deveTratarEntradaComEspacos() {
        when(paisRepository.findByNome("Estados Unidos")).thenReturn(Optional.empty());

        Pais paisNovo = new Pais();
        paisNovo.setNome("Estados Unidos");
        when(paisRepository.save(any(Pais.class))).thenReturn(paisNovo);

        paisService.obterOuCriarPais("estados unidos");

        verify(paisRepository).save(paisCaptor.capture());

        Pais paisSalvo = paisCaptor.getValue();
        assertEquals("Estados Unidos", paisSalvo.getNome());
    }

    @Test
    void deveCriarPaisQuandoRepositorioRetornarEmpty() {
        when(paisRepository.findByNome("Venezuela")).thenReturn(Optional.empty());

        Pais paisNovo = new Pais();
        paisNovo.setId(3L);
        paisNovo.setNome("Venezuela");
        when(paisRepository.save(any(Pais.class))).thenReturn(paisNovo);

        Pais resultado = paisService.obterOuCriarPais("Venezuela");

        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Venezuela", resultado.getNome());
        verify(paisRepository).save(any(Pais.class));
    }
}