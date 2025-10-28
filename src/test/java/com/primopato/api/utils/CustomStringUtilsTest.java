package com.primopato.api.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomStringUtilsTest {

    @Test
    void testFormataInicialMaiuscula_NuloOuVazio() {
        assertNull(CustomStringUtils.formataIncialMaiuscula(null));
        assertEquals("", CustomStringUtils.formataIncialMaiuscula(""));
    }

    @Test
    void testFormataInicialMaiuscula_Simples() {
        String result = CustomStringUtils.formataIncialMaiuscula("brasil");
        assertEquals("Brasil", result);
    }

    @Test
    void testFormataInicialMaiuscula_ComExcecoes() {
        String result = CustomStringUtils.formataIncialMaiuscula("reino de portugal");
        assertEquals("Reino de Portugal", result);
    }

    @Test
    void testFormataInicialMaiuscula_ComPontuacao() {
        String result = CustomStringUtils.formataIncialMaiuscula("ilha do sol!");
        assertTrue(result.startsWith("Ilha do Sol"));
    }

    @Test
    void testCoalesce() {
        assertEquals("A", CustomStringUtils.coalesce(null, "", "A", "B"));
        assertEquals("B", CustomStringUtils.coalesce(" ", "B", "C"));
        assertNull(CustomStringUtils.coalesce("", null, "   "));
    }
}
