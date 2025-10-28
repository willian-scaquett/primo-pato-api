package com.primopato.api.entity;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PaisTest {

    @Test
    void testConstrutorComNome() {
        Pais pais = new Pais("Brasil");
        assertEquals("Brasil", pais.getNome());
    }

    @Test
    void testEqualsAndHashCodeIguais() {
        Pais p1 = new Pais("Brasil");
        Pais p2 = new Pais("Brasil");
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testEqualsDiferentes() {
        Pais p1 = new Pais("Brasil");
        Pais p2 = new Pais("Argentina");
        assertNotEquals(p1, p2);
    }

    @Test
    void testEqualsMesmoObjeto() {
        Pais p = new Pais("Brasil");
        assertTrue(p.equals(p));
    }

    @Test
    void testEqualsComNull() {
        Pais p = new Pais("Brasil");
        assertFalse(p.equals(null));
    }

    @Test
    void testEqualsComClasseDiferente() {
        Pais p = new Pais("Brasil");
        assertFalse(p.equals("Brasil"));
    }

    @Test
    void testGetEstadosComListaNulaRetornaListaVazia() {
        Pais pais = new Pais("Brasil");
        pais.setEstados(null);
        assertNotNull(pais.getEstados());
        assertTrue(pais.getEstados().isEmpty());
    }

    @Test
    void testGetEstadosNaoNula() {
        Pais pais = new Pais("Brasil");
        Estado e = new Estado();
        e.setNome("São Paulo");
        pais.setEstados(List.of(e));
        assertEquals(1, pais.getEstados().size());
        assertEquals("São Paulo", pais.getEstados().getFirst().getNome());
    }

    @Test
    void testSettersEBasicos() {
        Pais pais = new Pais();
        pais.setId(1L);
        pais.setNome("Brasil");

        assertEquals(1L, pais.getId());
        assertEquals("Brasil", pais.getNome());
    }
}
