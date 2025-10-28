package com.primopato.api.service;

import com.primopato.api.entity.MissaoInfo;
import com.primopato.api.entity.Pato;
import com.primopato.api.repository.MissaoInfoRepository;
import com.primopato.api.service.assembler.MissaoInfoAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MissaoInfoServiceTest {

    private PatoService patoService;
    private MissaoInfoAssembler missaoInfoAssembler;
    private MissaoInfoRepository missaoInfoRepository;
    private MissaoInfoService missaoInfoService;

    @BeforeEach
    void setUp() {
        patoService = mock(PatoService.class);
        missaoInfoAssembler = mock(MissaoInfoAssembler.class);
        missaoInfoRepository = mock(MissaoInfoRepository.class);

        missaoInfoService = new MissaoInfoService(
                patoService,
                missaoInfoAssembler,
                missaoInfoRepository
        );
    }

    @Test
    void testObterOuCriarMissaoInfo_Existente() {
        Long idPato = 1L;
        String usuario = "usuario@teste.com";
        Pato pato = new Pato();
        MissaoInfo existente = new MissaoInfo();

        when(patoService.getPato(idPato, usuario)).thenReturn(pato);
        when(missaoInfoRepository.findByPatoAndPato_Usuario_usuario(pato, usuario))
                .thenReturn(Optional.of(existente));

        MissaoInfo resultado = missaoInfoService.obterOuCriarMissaoInfo(idPato, usuario);

        assertSame(existente, resultado);
        verify(patoService).getPato(idPato, usuario);
        verify(missaoInfoRepository).findByPatoAndPato_Usuario_usuario(pato, usuario);
        verifyNoMoreInteractions(missaoInfoAssembler, missaoInfoRepository);
    }

    @Test
    void testObterOuCriarMissaoInfo_CriaNova() {
        Long idPato = 2L;
        String usuario = "outro@teste.com";
        Pato pato = new Pato();
        MissaoInfo nova = new MissaoInfo();

        when(patoService.getPato(idPato, usuario)).thenReturn(pato);
        when(missaoInfoRepository.findByPatoAndPato_Usuario_usuario(pato, usuario))
                .thenReturn(Optional.empty());
        when(missaoInfoAssembler.montarMissaoInfo(pato, null)).thenReturn(nova);
        when(missaoInfoRepository.save(nova)).thenReturn(nova);

        MissaoInfo resultado = missaoInfoService.obterOuCriarMissaoInfo(idPato, usuario);

        assertSame(nova, resultado);
        verify(patoService).getPato(idPato, usuario);
        verify(missaoInfoRepository).findByPatoAndPato_Usuario_usuario(pato, usuario);
        verify(missaoInfoAssembler).montarMissaoInfo(pato, null);
        verify(missaoInfoRepository).save(nova);
    }
}
