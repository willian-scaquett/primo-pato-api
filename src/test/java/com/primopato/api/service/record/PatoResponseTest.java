package com.primopato.api.service.record;

import com.primopato.api.record.PatoResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PatoResponseTest {
    @Test
    public void deveCriarPatoResponse() {
        PatoResponse patoResponse = new PatoResponse(
                1L,
                "SN12345",
                "ModeloX",
                "patoResponseCorp",
                "Brasil",
                120.5f,
                15.3f,
                -23.5505,
                -46.6333,
                "Brasil",
                "SP",
                "São Paulo",
                "Parque Ibirapuera",
                "Av. Ibirapuera, 1000",
                0.95f,
                "Ativo",
                80,
                3,
                "Voo Infinito",
                "Super Velocidade",
                true
        );
        assertAll("patoResponse attributes",
                () -> assertEquals(1L, patoResponse.id()),
                () -> assertEquals("SN12345", patoResponse.numeroSerieDrone()),
                () -> assertEquals("ModeloX", patoResponse.modeloDrone()),
                () -> assertEquals("patoResponseCorp", patoResponse.fabricanteDrone()),
                () -> assertEquals("Brasil", patoResponse.pais()),
                () -> assertEquals(120.5f, patoResponse.altura()),
                () -> assertEquals(15.3f, patoResponse.peso()),
                () -> assertEquals(-23.5505, patoResponse.latitude()),
                () -> assertEquals(-46.6333, patoResponse.longitude()),
                () -> assertEquals("Brasil", patoResponse.pais()),
                () -> assertEquals("SP", patoResponse.estado()),
                () -> assertEquals("São Paulo", patoResponse.cidade()),
                () -> assertEquals("Parque Ibirapuera", patoResponse.pontoReferencia()),
                () -> assertEquals("Av. Ibirapuera, 1000", patoResponse.endereco()),
                () -> assertEquals(0.95f, patoResponse.precisao()),
                () -> assertEquals("Ativo", patoResponse.estadoHibernacao()),
                () -> assertEquals(80, patoResponse.bpm()),
                () -> assertEquals(3, patoResponse.quantidadeMutacoes()),
                () -> assertEquals("Voo Infinito", patoResponse.nomeSuperPoder()),
                () -> assertEquals("Super Velocidade", patoResponse.tipoSuperPoder()),
                () -> assertTrue(patoResponse.capturado()));
    }
}
