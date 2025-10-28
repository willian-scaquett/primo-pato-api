package com.primopato.api.record;

import com.primopato.api.entity.*;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.enumerated.TipoSuperPoder;
import com.primopato.api.utils.LocalizacaoUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatoRequestTest {

    @Test
    void testConstructorComValoresDiretos() {
        PatoRequest request = new PatoRequest(
                "123", "ModeloX", "FabricanteY", "Brasil",
                10f, 20f, -10, 30,
                "Brasil", "SP", "Campinas", "Perto do Lago", "Rua Azul",
                1f, EstadoHibernacao.DESPERTO, 100, 0,
                "Telepatia", TipoSuperPoder.PSIQUICO, true
        );

        assertEquals("123", request.numeroSerieDrone());
        assertEquals("ModeloX", request.modeloDrone());
        assertEquals("FabricanteY", request.fabricanteDrone());
        assertEquals("Brasil", request.paisDrone());
        assertEquals(10f, request.altura());
        assertEquals(20f, request.peso());
        assertEquals(-10, request.latitude());
        assertEquals(30, request.longitude());
        assertEquals(EstadoHibernacao.DESPERTO, request.estadoHibernacao());
        assertTrue(request.capturado());
    }

    @Test
    void testConstructorComPato() {
        Pais pais = new Pais("Brasil");
        FabricanteDrone fab = new FabricanteDrone();
        fab.setNome("FabricanteY");
        fab.setPais(pais);

        ModeloDrone modelo = new ModeloDrone();
        modelo.setNome("ModeloX");
        modelo.setFabricante(fab);

        Drone drone = new Drone();
        drone.setNumeroSerie("123");
        drone.setModelo(modelo);

        Localizacao localizacao = getLocalizacao();

        SuperPoder sp = new SuperPoder();
        sp.setNome("Telepatia");
        sp.setTipo(TipoSuperPoder.PSIQUICO);

        Pato pato = new Pato();
        pato.setDroneQueEncontrou(drone);
        pato.setAltura(10f);
        pato.setPeso(20f);
        pato.setLocalizacao(localizacao);
        pato.setPrecisaoDoGpsQuandoEncontrado(1f);
        pato.setEstadoHibernacao(EstadoHibernacao.DESPERTO);
        pato.setBpm(120);
        pato.setQuantidadeMutacoes(2);
        pato.setSuperPoder(sp);
        pato.setCapturado(true);

        PatoRequest req = new PatoRequest(pato);

        assertEquals("123", req.numeroSerieDrone());
        assertEquals("ModeloX", req.modeloDrone());
        assertEquals("FabricanteY", req.fabricanteDrone());
        assertEquals("Brasil", req.paisDrone());
        assertEquals(-10, req.latitude());
        assertEquals(30, req.longitude());
        assertEquals("SP", req.estado());
        assertEquals("Campinas", req.cidade());
        assertEquals("Telepatia", req.nomeSuperPoder());
        assertEquals(TipoSuperPoder.PSIQUICO, req.tipoSuperPoder());
        assertTrue(req.capturado());
    }

    private static Localizacao getLocalizacao() {
        Coordenadas coord = new Coordenadas(-10d, 30d);
        Pais paisCidade = new Pais("Brasil");
        Estado estado = new Estado();
        estado.setNome("SP");
        estado.setPais(paisCidade);
        Cidade cidade = new Cidade();
        cidade.setNome("Campinas");
        cidade.setEstado(estado);

        Localizacao localizacao = new Localizacao();
        localizacao.setCidade(cidade);
        localizacao.setCoordenadas(coord);
        localizacao.setEndereco("Rua Azul");
        localizacao.setPontoReferencia("Perto do Lago");
        return localizacao;
    }

    @Test
    void testConstructorComPatoSemSuperPoder() {
        Pais pais = new Pais("Brasil");
        FabricanteDrone fab = new FabricanteDrone();
        fab.setNome("FabricanteZ");
        fab.setPais(pais);

        ModeloDrone modelo = new ModeloDrone();
        modelo.setNome("ModeloY");
        modelo.setFabricante(fab);

        Drone drone = new Drone();
        drone.setNumeroSerie("ABC");
        drone.setModelo(modelo);

        Coordenadas coord = new Coordenadas(1d, 1d);
        Pais paisCidade = new Pais("Brasil");
        Estado estado = new Estado();
        estado.setNome("RJ");
        estado.setPais(paisCidade);
        Cidade cidade = new Cidade();
        cidade.setNome("Rio");
        cidade.setEstado(estado);

        Localizacao localizacao = new Localizacao();
        localizacao.setCidade(cidade);
        localizacao.setCoordenadas(coord);

        Pato pato = new Pato();
        pato.setDroneQueEncontrou(drone);
        pato.setAltura(10f);
        pato.setPeso(20f);
        pato.setLocalizacao(localizacao);
        pato.setPrecisaoDoGpsQuandoEncontrado(2f);
        pato.setEstadoHibernacao(EstadoHibernacao.HIBERNACAO_PROFUNDA);
        pato.setCapturado(false);
        pato.setQuantidadeMutacoes(2);

        PatoRequest req = new PatoRequest(pato);

        assertEquals("ABC", req.numeroSerieDrone());
        assertEquals("ModeloY", req.modeloDrone());
        assertNull(req.nomeSuperPoder());
        assertNull(req.tipoSuperPoder());
        assertFalse(req.capturado());
    }

    @Test
    void testConstructorComPaisEUA() {
        Pais paisEua = LocalizacaoUtils.EUA;
        FabricanteDrone fabricante = new FabricanteDrone();
        fabricante.setNome("LockMart");
        fabricante.setPais(paisEua);

        ModeloDrone modelo = new ModeloDrone();
        modelo.setNome("Falcon");
        modelo.setFabricante(fabricante);

        Drone drone = new Drone();
        drone.setNumeroSerie("EUA-001");
        drone.setModelo(modelo);

        Coordenadas coordenadas = new Coordenadas(37.7749, -122.4194);
        Estado estado = new Estado();
        estado.setNome("California");
        estado.setPais(paisEua);
        Cidade cidade = new Cidade();
        cidade.setNome("San Francisco");
        cidade.setEstado(estado);

        Localizacao localizacao = new Localizacao();
        localizacao.setCidade(cidade);
        localizacao.setCoordenadas(coordenadas);
        localizacao.setEndereco("Market St");
        localizacao.setPontoReferencia("Perto da ponte");

        Pato pato = new Pato();
        pato.setDroneQueEncontrou(drone);
        pato.setAltura(100f); // 100 cm = 3.28 pés
        pato.setPeso(453.59237f); // 453.59237 g = 1 libra
        pato.setLocalizacao(localizacao);
        pato.setPrecisaoDoGpsQuandoEncontrado(0.5f);
        pato.setEstadoHibernacao(EstadoHibernacao.DESPERTO);
        pato.setCapturado(true);
        pato.setQuantidadeMutacoes(1);

        PatoRequest req = new PatoRequest(pato);

        assertEquals("EUA-001", req.numeroSerieDrone());
        assertEquals("Falcon", req.modeloDrone());
        assertEquals("LockMart", req.fabricanteDrone());
        assertEquals("Estados Unidos da América", req.paisDrone());
        assertEquals("California", req.estado());
        assertEquals("San Francisco", req.cidade());
        assertEquals(100f / 30.48f, req.altura(), 0.001f); // convertido para pés
        assertEquals(1.0f, req.peso(), 0.001f); // convertido para libras
    }
}
