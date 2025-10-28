package com.primopato.api.record;

import com.primopato.api.record.MissaoInfoResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MissaoInfoResponseTest {

    @Test
    void deveCriarMissaoInfoResponse() {
        MissaoInfoResponse missaoInfoResponse = new MissaoInfoResponse(
                42L,                           // idPato
                "Usar barreira de energia quântica", // defesaRecomendada
                12.75,                               // distancia (km)
                8.45,                                // rendimentoCombustivelIda (km/L)
                7.98,                                // rendimentoCombustivelVolta (km/L)
                1.51,                                // gastoCombustivelIda (L)
                1.60,                                // gastoCombustivelVolta (L)
                3.11,                                // gastoCombustivelTotal (L)
                0.23f,                               // risco (23%)
                8.75f,                               // ganhoCientifico
                5.40f,                               // ganhoParanormal
                "Rifle de plasma",                   // armaRecomendada
                "Ataque furtivo aéreo",              // abordagemRecomendada
                "Grande (15x20m)",                   // tamanhoRedeNecessaria
                new BigDecimal("1285.45"));      // custo (em créditos ou R$));
        assertAll(
                () -> assertEquals(42L, missaoInfoResponse.idPato()),
                () -> assertEquals("Usar barreira de energia quântica", missaoInfoResponse.defesaRecomendada()),
                () -> assertEquals(12.75, missaoInfoResponse.distancia(), 0.001),
                () -> assertEquals(8.45, missaoInfoResponse.rendimentoCombustivelIda(), 0.001),
                () -> assertEquals(7.98, missaoInfoResponse.rendimentoCombustivelVolta(), 0.001),
                () -> assertEquals(1.51, missaoInfoResponse.gastoCombustivelIda(), 0.001),
                () -> assertEquals(1.60, missaoInfoResponse.gastoCombustivelVolta(), 0.001),
                () -> assertEquals(3.11, missaoInfoResponse.gastoCombustivelTotal(), 0.001),
                () -> assertEquals(0.23f, missaoInfoResponse.risco(), 0.001),
                () -> assertEquals(8.75f, missaoInfoResponse.ganhoCientifico(), 0.001),
                () -> assertEquals(5.40f, missaoInfoResponse.ganhoParanormal(), 0.001),
                () -> assertEquals("Rifle de plasma", missaoInfoResponse.armaRecomendada()),
                () -> assertEquals("Ataque furtivo aéreo", missaoInfoResponse.abordagemRecomendada()),
                () -> assertEquals("Grande (15x20m)", missaoInfoResponse.tamanhoRedeNecessaria()),
                () -> assertEquals(new BigDecimal("1285.45"), missaoInfoResponse.custo())
        );
    }
}
