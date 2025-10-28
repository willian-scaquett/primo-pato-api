package com.primopato.api.enumerated;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class TamanhoRedeTest {

    @Test
    void testAtributos() {
        assertEquals("Pequena", TamanhoRede.PEQUENA.getNome());
        assertEquals(new BigDecimal("100"), TamanhoRede.PEQUENA.getPreco());
        assertEquals(0f, TamanhoRede.PEQUENA.getMin());
        assertEquals(1000f, TamanhoRede.PEQUENA.getMax());

        assertEquals("Gigante", TamanhoRede.GIGANTE.getNome());
        assertEquals(new BigDecimal("1000"), TamanhoRede.GIGANTE.getPreco());
        assertEquals(10001f, TamanhoRede.GIGANTE.getMin());
        assertEquals(Float.MAX_VALUE, TamanhoRede.GIGANTE.getMax());
    }

    @Test
    void testPorAlturaPatoFaixasCorretas() {
        assertEquals(TamanhoRede.PEQUENA, TamanhoRede.porAlturaPato(0));
        assertEquals(TamanhoRede.PEQUENA, TamanhoRede.porAlturaPato(999.9f));
        assertEquals(TamanhoRede.MEDIA, TamanhoRede.porAlturaPato(1500f));
        assertEquals(TamanhoRede.GRANDE, TamanhoRede.porAlturaPato(3000f));
        assertEquals(TamanhoRede.EXTRA_GRANDE, TamanhoRede.porAlturaPato(8000f));
        assertEquals(TamanhoRede.GIGANTE, TamanhoRede.porAlturaPato(15000f));
    }

    @Test
    void testPorAlturaPatoLimites() {
        assertEquals(TamanhoRede.PEQUENA, TamanhoRede.porAlturaPato(0));
        assertEquals(TamanhoRede.MEDIA, TamanhoRede.porAlturaPato(1001f));
        assertEquals(TamanhoRede.GRANDE, TamanhoRede.porAlturaPato(2001f));
        assertEquals(TamanhoRede.EXTRA_GRANDE, TamanhoRede.porAlturaPato(5001f));
        assertEquals(TamanhoRede.GIGANTE, TamanhoRede.porAlturaPato(10001f));
    }

    @Test
    void testPorAlturaPatoAbaixoDoMinimo() {
        assertEquals(TamanhoRede.PEQUENA, TamanhoRede.porAlturaPato(-10f));
    }

    @Test
    void testPorAlturaPatoMuitoGrande() {
        assertEquals(TamanhoRede.GIGANTE, TamanhoRede.porAlturaPato(Float.MAX_VALUE));
    }
}
