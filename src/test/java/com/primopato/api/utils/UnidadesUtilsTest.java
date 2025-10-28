package com.primopato.api.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnidadesUtilsTest {

    @Test
    void testPeParaCentimetro() {
        Float result = UnidadesUtils.peParaCentimetro(2f);
        assertEquals(60.96f, result, 0.0001f);
    }

    @Test
    void testCentimetroParaPe() {
        Float result = UnidadesUtils.centimetroParaPe(30.48f);
        assertEquals(1f, result, 0.0001f);
    }

    @Test
    void testLibraParaGrama() {
        Float result = UnidadesUtils.libraParaGrama(1f);
        assertEquals(453.59237f, result, 0.0001f);
    }

    @Test
    void testGramaParaLibra() {
        Float result = UnidadesUtils.gramaParaLibra(453.59237f);
        assertEquals(1f, result, 0.0001f);
    }

    @Test
    void testConversoesInversas() {
        float original = 5f;
        Float gramas = UnidadesUtils.libraParaGrama(original);
        Float libras = UnidadesUtils.gramaParaLibra(gramas);
        assertEquals(original, libras, 0.0001f);

        Float cm = UnidadesUtils.peParaCentimetro(original);
        Float pes = UnidadesUtils.centimetroParaPe(cm);
        assertEquals(original, pes, 0.0001f);
    }
}
