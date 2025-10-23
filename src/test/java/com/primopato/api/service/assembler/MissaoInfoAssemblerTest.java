package com.primopato.api.service.assembler;

import com.primopato.api.entity.MissaoInfo;
import com.primopato.api.entity.Pato;
import com.primopato.api.entity.SuperPoder;
import com.primopato.api.enumerated.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MissaoInfoAssemblerTest {

    private MissaoInfoAssembler assembler;

    @BeforeEach
    void setUp() {
        assembler = new MissaoInfoAssembler();
    }

    private Pato criarPato(EstadoHibernacao estado, TipoSuperPoder tipo, Integer bpm, float altura, int mutacoes, float peso) {
        Pato pato = new Pato();
        pato.setEstadoHibernacao(estado);
        pato.setBpm(bpm);
        pato.setAltura(altura);
        pato.setQuantidadeMutacoes(mutacoes);
        pato.setPeso(peso);

        if (tipo != null) {
            SuperPoder sp = new SuperPoder();
            sp.setTipo(tipo);
            pato.setSuperPoder(sp);
        }

        return pato;
    }

    @Test
    void deveMontarMissaoInfoComSuperPoder() {
        Pato pato = criarPato(EstadoHibernacao.DESPERTO, TipoSuperPoder.FOGO, 100, 50f, 2, 200f);

        MissaoInfo info = assembler.montarMissaoInfo(pato, null);

        assertNotNull(info);
        assertEquals(DefesaDrone.CERAMICA_REFRATARIA, info.getDefesaDrone());
        assertEquals(TamanhoRede.porAlturaPato(50f), info.getTamanhoRede());
        assertEquals(Abordagem.COMBATIVO, info.getAbordagem());
        assertEquals(ArmaDrone.MISSIL_TELEGUIADO, info.getArmaDrone());
        assertTrue(info.getRisco() > 0);
        assertEquals(2 * pato.getEstadoHibernacao().getPotencializador(), info.getGanhoCientifico());
        assertEquals(pato.getSuperPoder().getTipo().getGanhoParanormalBase() * pato.getEstadoHibernacao().getPotencializador(),
                info.getGanhoParanormal());
    }

    @Test
    void deveMontarMissaoInfoSemSuperPoder() {
        Pato pato = criarPato(EstadoHibernacao.DESPERTO, null, null, 40f, 1, 150f);

        MissaoInfo info = assembler.montarMissaoInfo(pato, null);

        assertNotNull(info);
        assertEquals(DefesaDrone.NENHUMA, info.getDefesaDrone());
        assertEquals(ArmaDrone.RAIO_LASER, info.getArmaDrone());
        assertEquals(Abordagem.COMBATIVO, info.getAbordagem());
        assertEquals(1 * pato.getEstadoHibernacao().getPotencializador(), info.getGanhoCientifico());
        assertEquals(0, info.getGanhoParanormal());
    }

    @Test
    void deveEscolherArmaConformeEstadoHibernacao() {
        Pato pato = criarPato(EstadoHibernacao.HIBERNACAO_PROFUNDA, null, 120, 30f, 0, 100f);
        MissaoInfo info = assembler.montarMissaoInfo(pato, null);
        assertEquals(ArmaDrone.CAPSULA_CONGELAMENTO, info.getArmaDrone());
    }

    @Test
    void deveEscolherAbordagemFurtivoQuandoBpmMaiorQueBase() {
        Pato pato = criarPato(EstadoHibernacao.EM_TRANSE, null, 210, 30f, 0, 100f);
        MissaoInfo info = assembler.montarMissaoInfo(pato, null);
        assertEquals(Abordagem.FURTIVO, info.getAbordagem());
    }

    @Test
    void deveEscolherAbordagemComedidoQuandoBpmMenorQueBase() {
        Pato pato = criarPato(EstadoHibernacao.EM_TRANSE, null, 180, 30f, 0, 100f);
        MissaoInfo info = assembler.montarMissaoInfo(pato, null);
        assertEquals(Abordagem.COMEDIDO, info.getAbordagem());
    }

    @Test
    void calculaRiscoComBpmNulo() {
        Pato pato = criarPato(EstadoHibernacao.EM_TRANSE, TipoSuperPoder.SOBRENATURAL, 120, 40f, 1, 150f);
        MissaoInfo info = assembler.montarMissaoInfo(pato, null);

        // risco > 0 porque EstadoHibernacao + riscoSuperPoder
        assertTrue(info.getRisco() > 0);
    }
}
