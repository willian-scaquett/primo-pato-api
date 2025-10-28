package com.primopato.api.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocalizacaoTest {

    @Test
    void testEqualsAndHashCodeIguais() {
        Coordenadas coord1 = new Coordenadas(10.0, 20.0);
        Coordenadas coord2 = new Coordenadas(10.0, 20.0);

        Localizacao l1 = new Localizacao();
        l1.setCoordenadas(coord1);
        Localizacao l2 = new Localizacao();
        l2.setCoordenadas(coord2);

        assertEquals(l1, l2);
        assertEquals(l1.hashCode(), l2.hashCode());
    }

    @Test
    void testEqualsDiferentes() {
        Coordenadas coord1 = new Coordenadas(10.0, 20.0);
        Coordenadas coord2 = new Coordenadas(11.0, 22.0);

        Localizacao l1 = new Localizacao();
        l1.setCoordenadas(coord1);
        Localizacao l2 = new Localizacao();
        l2.setCoordenadas(coord2);

        assertNotEquals(l1, l2);
    }

    @Test
    void testEqualsMesmoObjeto() {
        Localizacao l = new Localizacao();
        l.setCoordenadas(new Coordenadas(1.0, 2.0));
        assertTrue(l.equals(l));
    }

    @Test
    void testEqualsComNull() {
        Localizacao l = new Localizacao();
        l.setCoordenadas(new Coordenadas(1.0, 2.0));
        assertFalse(l.equals(null));
    }

    @Test
    void testEqualsComClasseDiferente() {
        Localizacao l = new Localizacao();
        l.setCoordenadas(new Coordenadas(1.0, 2.0));
        assertFalse(l.equals("String diferente"));
    }

    @Test
    void testCamposBasicos() {
        Localizacao l = new Localizacao();
        l.setEndereco("Rua Azul");
        l.setPontoReferencia("Perto do Lago");
        Cidade cidade = new Cidade();
        cidade.setNome("Campinas");
        l.setCidade(cidade);

        assertEquals("Rua Azul", l.getEndereco());
        assertEquals("Perto do Lago", l.getPontoReferencia());
        assertEquals("Campinas", l.getCidade().getNome());
    }
}
