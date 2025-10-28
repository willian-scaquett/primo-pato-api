package com.primopato.api.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordenadasTest {

    @Test
    void testEqualsAndHashCode() {
        Coordenadas c1 = new Coordenadas(10.0, 20.0);
        Coordenadas c2 = new Coordenadas(10.0, 20.0);
        Coordenadas c3 = new Coordenadas(10.0, 30.0);

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1, c3);
    }

    @Test
    void testEqualsMesmoObjeto() {
        Coordenadas c = new Coordenadas(10.0, 20.0);
        assertEquals(c, c);
    }

    @Test
    void testEqualsComNull() {
        Coordenadas c = new Coordenadas(10.0, 20.0);
        assertNotEquals(null, c);
    }

    @Test
    void testEqualsComClasseDiferente() {
        Coordenadas c = new Coordenadas(10.0, 20.0);
        assertNotEquals("outroObjeto", c);
    }

    @Test
    void testConstrutoresEGetters() {
        Coordenadas c = new Coordenadas(5.5, 9.9);
        assertEquals(5.5, c.getLatitude());
        assertEquals(9.9, c.getLongitude());

        Coordenadas vazio = new Coordenadas();
        vazio.setLatitude(1.1);
        vazio.setLongitude(2.2);
        assertEquals(1.1, vazio.getLatitude());
        assertEquals(2.2, vazio.getLongitude());
    }
}
