package com.primopato.api.service.assembler;

import com.primopato.api.entity.*;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.enumerated.TipoSuperPoder;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.utils.LocalizacaoUtils;
import com.primopato.api.utils.UnidadesUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatoAssemblerTest {

    private PatoAssembler patoAssembler;

    @BeforeEach
    void setUp() {
        patoAssembler = new PatoAssembler();
    }

    @Test
    void deveMontarPatoComSucesso() {
        // Arrange
        Usuario usuario = new Usuario();
        Drone drone = new Drone();
        SuperPoder superPoder = new SuperPoder();
        superPoder.setNome("Raio");
        superPoder.setTipo(TipoSuperPoder.ELETRICIDADE);

        Pais pais = new Pais();
        Localizacao localizacao = new Localizacao();
        FabricanteDrone fabricanteDrone = new FabricanteDrone();
        fabricanteDrone.setPais(pais);
        ModeloDrone modeloDrone = new ModeloDrone();
        modeloDrone.setFabricante(fabricanteDrone);
        drone.setModelo(modeloDrone);
        Coordenadas coordenadas = new Coordenadas();
        coordenadas.setLatitude(10.0);
        coordenadas.setLongitude(20.0);
        localizacao.setCoordenadas(coordenadas);

        PatoRequest request = new PatoRequest(
                "123ABC",       // numeroSerieDrone
                "DroneX",       // modeloDrone
                "ACME",         // fabricanteDrone
                "Brasil",       // paisDrone
                10f,            // altura
                100f,           // peso
                10.0,           // latitude
                20.0,           // longitude
                "Brasil",       // pais
                "SP",           // estado
                "Campinas",     // cidade
                "Parque Central", // pontoReferencia
                "Rua A, 123",   // endereco
                0.5f,           // precisao
                EstadoHibernacao.DESPERTO, // estadoHibernacao
                null,           // bpm
                2,              // quantidadeMutacoes
                "Raio",          // nomeSuperPoder
                TipoSuperPoder.ELETRICIDADE, // tipoSuperPoder
                true            // capturado
        );

        // Act
        Pato pato = patoAssembler.montarPato(request, usuario, drone, superPoder, pais, localizacao);

        // Assert
        assertNotNull(pato);
        assertEquals(usuario, pato.getUsuario());
        assertEquals(localizacao, pato.getLocalizacao());
        assertEquals(drone, pato.getDroneQueEncontrou());
        assertEquals(superPoder, pato.getSuperPoder());
        assertNull(pato.getBpm());
        assertEquals(2, pato.getQuantidadeMutacoes());
        assertEquals(EstadoHibernacao.DESPERTO, pato.getEstadoHibernacao());
    }

    @Test
    void deveEditarPatoComSucesso() {
        Pais paisExistente = new Pais();
        paisExistente.setNome("Brasil");
        FabricanteDrone fabricanteExistente = new FabricanteDrone();
        fabricanteExistente.setPais(paisExistente);
        ModeloDrone modeloExistente = new ModeloDrone();
        modeloExistente.setFabricante(fabricanteExistente);
        Drone droneExistente = new Drone();
        droneExistente.setModelo(modeloExistente);
        Pato patoExistente = new Pato();
        patoExistente.setDroneQueEncontrou(droneExistente);

        PatoRequest request = new PatoRequest(
                "123ABC",
                "DroneX",
                "ACME",
                "Brasil",
                15f,
                200f,
                10.0,
                20.0,
                "Brasil",
                "SP",
                "Campinas",
                "Parque Central",
                "Rua A, 123",
                0.5f,
                EstadoHibernacao.HIBERNACAO_PROFUNDA,
                180,
                3,
                null,
                null,
                false
        );

        Drone drone = new Drone();
        SuperPoder superPoder = new SuperPoder();
        Pais pais = new Pais();
        Localizacao localizacao = new Localizacao();
        FabricanteDrone fabricanteDrone = new FabricanteDrone();
        fabricanteDrone.setPais(pais);
        ModeloDrone modeloDrone = new ModeloDrone();
        modeloDrone.setFabricante(fabricanteDrone);
        drone.setModelo(modeloDrone);

        Pato patoEditado = patoAssembler.editarPato(request, drone, superPoder, pais, localizacao, patoExistente);

        assertNotNull(patoEditado);
        assertEquals(patoExistente, patoEditado);
        assertEquals(drone, patoEditado.getDroneQueEncontrou());
        assertEquals(localizacao, patoEditado.getLocalizacao());
        assertEquals(3, patoEditado.getQuantidadeMutacoes());
        assertEquals(EstadoHibernacao.HIBERNACAO_PROFUNDA, patoEditado.getEstadoHibernacao());
        assertEquals(180, patoEditado.getBpm());
        assertNull(patoEditado.getSuperPoder());
    }

    @Test
    void deveConverterValoresQuandoPaisEua() {
        PatoRequest request = new PatoRequest(
                "777",
                "DroneY",
                "ACME",
                "EUA",
                6f,   // altura em pés
                10f,  // peso em libras
                40.0,
                -70.0,
                "EUA",
                "NY",
                "New York",
                "Central Park",
                "Rua B, 100",
                2f,   // precisão em jardas
                EstadoHibernacao.DESPERTO,
                null,
                1,
                "Raio",
                TipoSuperPoder.ELETRICIDADE,
                false
        );

        Drone drone = new Drone();
        SuperPoder superPoder = new SuperPoder();
        Pato pato = new Pato();
        Localizacao localizacao = new Localizacao();
        Pais pais = LocalizacaoUtils.EUA;
        FabricanteDrone fabricanteDrone = new FabricanteDrone();
        fabricanteDrone.setPais(pais);
        ModeloDrone modeloDrone = new ModeloDrone();
        modeloDrone.setFabricante(fabricanteDrone);
        drone.setModelo(modeloDrone);

        Pato resultado = patoAssembler.definirPato(request, drone, superPoder, localizacao, pato);

        assertEquals(UnidadesUtils.peParaCentimetro(6f), resultado.getAltura());
        assertEquals(UnidadesUtils.libraParaGrama(10f), resultado.getPeso());
    }

    @Test
    void deveDefinirSuperPoderQuandoDespertoESetarBpmQuandoHibernando() {
        PatoRequest requestDesperto = new PatoRequest(
                "1", "DroneA", "ACME", "Brasil",
                20f, 50f, 0.0, 0.0,
                "Brasil", "SP", "Campinas",
                "Ponto", "Endereco", 10f,
                EstadoHibernacao.DESPERTO, null, 0,
                "Raio", TipoSuperPoder.ELETRICIDADE, false
        );

        PatoRequest requestHibernando = new PatoRequest(
                "2", "DroneB", "ACME", "Brasil",
                20f, 50f, 0.0, 0.0,
                "Brasil", "SP", "Campinas",
                "Ponto", "Endereco", 10f,
                EstadoHibernacao.HIBERNACAO_PROFUNDA, 150, 0,
                null, null, false
        );

        Drone drone = new Drone();
        SuperPoder superPoder = new SuperPoder();
        superPoder.setNome("Raio");
        superPoder.setTipo(TipoSuperPoder.ELETRICIDADE);

        Pais pais = new Pais();
        Localizacao localizacao = new Localizacao();
        FabricanteDrone fabricanteDrone = new FabricanteDrone();
        fabricanteDrone.setPais(pais);
        ModeloDrone modeloDrone = new ModeloDrone();
        modeloDrone.setFabricante(fabricanteDrone);
        drone.setModelo(modeloDrone);

        Pato patoDesperto = patoAssembler.definirPato(requestDesperto, drone, superPoder, localizacao, new Pato());
        assertEquals(superPoder, patoDesperto.getSuperPoder());
        assertNull(patoDesperto.getBpm());

        Pato patoHibernando = patoAssembler.definirPato(requestHibernando, drone, superPoder, localizacao, new Pato());
        assertEquals(150, patoHibernando.getBpm());
        assertNull(patoHibernando.getSuperPoder());
    }
}
