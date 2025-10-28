package com.primopato.api.entity;

import com.primopato.api.enumerated.*;
import com.primopato.api.utils.LocalizacaoUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MissaoInfoTest {

    @Test
    void testSetRiscoLimite() {
        MissaoInfo info = new MissaoInfo();
        info.setRisco(150f);
        assertEquals(100f, info.getRisco());
        info.setRisco(80f);
        assertEquals(80f, info.getRisco());
    }

    @Test
    void testSetGanhoCientificoLimite() {
        MissaoInfo info = new MissaoInfo();
        info.setGanhoCientifico(200f);
        assertEquals(100f, info.getGanhoCientifico());
        info.setGanhoCientifico(50f);
        assertEquals(50f, info.getGanhoCientifico());
    }

    @Test
    void testSetGanhoParanormalLimite() {
        MissaoInfo info = new MissaoInfo();
        info.setGanhoParanormal(200f);
        assertEquals(100f, info.getGanhoParanormal());
        info.setGanhoParanormal(40f);
        assertEquals(40f, info.getGanhoParanormal());
    }

    @Test
    void testSetDesempenhoCombustivelPosCapturaMinimo() {
        MissaoInfo info = new MissaoInfo();
        info.setDesempenhoCombustivelPorLitroPosCaputura(0.2f);
        assertEquals(1f, info.getDesempenhoCombustivelPorLitroPosCaputura());
        info.setDesempenhoCombustivelPorLitroPosCaputura(10f);
        assertEquals(10f, info.getDesempenhoCombustivelPorLitroPosCaputura());
    }

    @Test
    void testGetCustoCalculado() {
        MissaoInfo info = new MissaoInfo();
        info.setDefesaDrone(DefesaDrone.ALHO);
        info.setArmaDrone(ArmaDrone.CAPSULA_CONGELAMENTO);
        info.setTamanhoRede(TamanhoRede.MEDIA);

        BigDecimal custo = info.getCusto(10.0);
        BigDecimal esperado = DefesaDrone.ALHO.getPreco()
                .add(new BigDecimal("12.12").multiply(BigDecimal.valueOf(10)))
                .add(ArmaDrone.CAPSULA_CONGELAMENTO.getPreco())
                .add(TamanhoRede.MEDIA.getPreco());
        assertEquals(0, custo.compareTo(esperado));
    }

    @Test
    void testGetDistanciaEGastosCombustivel() {
        Pato pato = new Pato();
        Localizacao local = new Localizacao();
        Coordenadas coord = new Coordenadas(LocalizacaoUtils.LATITUDE_DSIN + 0.1, LocalizacaoUtils.LONGITUDE_DSIN + 0.1);
        local.setCoordenadas(coord);
        pato.setLocalizacao(local);

        MissaoInfo info = new MissaoInfo();
        info.setPato(pato);
        info.setDesempenhoCombustivelPorLitroPosCaputura(10f);

        double distancia = info.getDistancia();
        assertTrue(distancia > 0);

        double ida = info.getGastoCombustivelIda();
        double volta = info.getGastoCombustivelVolta();

        assertEquals(distancia / LocalizacaoUtils.COMBUSTIVEL_KM_L, ida, 0.0001);
        assertEquals(distancia / 10f, volta, 0.0001);
    }
}
