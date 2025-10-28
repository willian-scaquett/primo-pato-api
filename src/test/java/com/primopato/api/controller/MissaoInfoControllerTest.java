package com.primopato.api.controller;

import com.primopato.api.entity.*;
import com.primopato.api.enumerated.Abordagem;
import com.primopato.api.enumerated.ArmaDrone;
import com.primopato.api.enumerated.DefesaDrone;
import com.primopato.api.enumerated.TamanhoRede;
import com.primopato.api.record.MissaoInfoResponse;
import com.primopato.api.service.MissaoInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MissaoInfoControllerTest {

    @Test
    void testBuscar() {
        MissaoInfoService missaoInfoService = mock(MissaoInfoService.class);
        MissaoInfoController controller = new MissaoInfoController(missaoInfoService);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("usuario@teste.com");

        // Mock completo do MissaoInfo e dependências
        Pato pato = new Pato();
        pato.setId(1L);

        DefesaDrone defesa = DefesaDrone.ESCUDO_ADAPTATIVO_IA;
        ArmaDrone arma = ArmaDrone.CAPSULA_CONGELAMENTO;
        Abordagem abordagem = Abordagem.COMBATIVO;
        TamanhoRede tamanhoRede = TamanhoRede.GIGANTE;

        MissaoInfo missaoInfo = mock(MissaoInfo.class);
        when(missaoInfo.getPato()).thenReturn(pato);
        when(missaoInfo.getDefesaDrone()).thenReturn(defesa);
        when(missaoInfo.getArmaDrone()).thenReturn(arma);
        when(missaoInfo.getAbordagem()).thenReturn(abordagem);
        when(missaoInfo.getTamanhoRede()).thenReturn(tamanhoRede);
        when(missaoInfo.getDistancia()).thenReturn(150.5);
        when(missaoInfo.getDesempenhoCombustivelPorLitroPosCaputura()).thenReturn(290.0f);
        when(missaoInfo.getGastoCombustivelIda()).thenReturn(10.0);
        when(missaoInfo.getGastoCombustivelVolta()).thenReturn(8.0);
        when(missaoInfo.getRisco()).thenReturn(2.5f);
        when(missaoInfo.getGanhoCientifico()).thenReturn(12.3f);
        when(missaoInfo.getGanhoParanormal()).thenReturn(4.7f);
        when(missaoInfo.getCusto(18.0)).thenReturn(BigDecimal.valueOf(999.99));

        when(missaoInfoService.obterOuCriarMissaoInfo(1L, "usuario@teste.com"))
                .thenReturn(missaoInfo);

        ResponseEntity<MissaoInfoResponse> response = controller.buscar(auth, 1L);

        assertEquals(200, response.getStatusCodeValue());
        MissaoInfoResponse body = response.getBody();
        assertNotNull(body);
        assertEquals(1L, body.idPato());
        assertEquals("Escudo adaptativo com IA", body.defesaRecomendada());
        assertEquals("Cápsula de Congelamento", body.armaRecomendada());
        assertEquals("Combativo", body.abordagemRecomendada());
        assertEquals("Gigante", body.tamanhoRedeNecessaria());
        verify(missaoInfoService).obterOuCriarMissaoInfo(1L, "usuario@teste.com");
    }
}
