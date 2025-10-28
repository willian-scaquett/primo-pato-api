package com.primopato.api.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocalizacaoUtilsTest {

    @Test
    void testDistanciaKmEntreCoordenadas_MesmoPonto() {
        double d = LocalizacaoUtils.distanciaKmEntreCoordenadas(0, 0, 0, 0);
        assertEquals(0.0, d, 0.0001);
    }

    @Test
    void testDistanciaKmEntreCoordenadas_DistanciaConhecida() {
        double d = LocalizacaoUtils.distanciaKmEntreCoordenadas(0, 0, 0, 1);
        assertTrue(d > 100 && d < 120); // 111 km aproximadamente
    }

    @Test
    void testDistanciaKmEntreDsinECoordenadas() {
        double d = LocalizacaoUtils.distanciaKmEntreDsinECoordenadas(-22.21389, -49.94583);
        assertEquals(0.0, d, 0.0001);
    }

    @Test
    void testConstantes() {
        assertNotNull(LocalizacaoUtils.EUA);
        assertEquals("Estados Unidos da América", LocalizacaoUtils.EUA.getNome());
        assertEquals(300f, LocalizacaoUtils.COMBUSTIVEL_KM_L);
        assertEquals(-22.21389, LocalizacaoUtils.LATITUDE_DSIN);
        assertEquals(-49.94583, LocalizacaoUtils.LONGITUDE_DSIN);
    }
}
