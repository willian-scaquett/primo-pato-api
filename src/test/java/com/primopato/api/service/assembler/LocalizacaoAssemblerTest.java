package com.primopato.api.service.assembler;

import com.primopato.api.entity.Cidade;
import com.primopato.api.entity.Coordenadas;
import com.primopato.api.entity.Localizacao;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.service.geocoding.GeocodingResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocalizacaoAssemblerTest {

    @InjectMocks
    private LocalizacaoAssembler localizacaoAssembler;

    private PatoRequest patoRequest;
    private Cidade cidade;
    private GeocodingResult geoResult;

    @BeforeEach
    void setUp() {
        cidade = new Cidade();
        cidade.setId(1L);
        cidade.setNome("São Paulo");

        patoRequest = new PatoRequest(
                "DRONE-001",                    // numeroSerieDrone
                "Phantom 4",                    // modeloDrone
                "DJI",                          // fabricanteDrone
                "China",                        // paisDrone
                15.5f,                          // altura
                2.3f,                           // peso
                -23.550520,                     // latitude
                -46.633308,                     // longitude
                "Brasil",                       // pais
                "São Paulo",                    // estado
                "São Paulo",                    // cidade
                "Próximo ao lago",              // pontoReferencia
                "Av. Paulista, 1000",           // endereco
                0.5f,                           // precisao
                EstadoHibernacao.DESPERTO,         // estadoHibernacao
                120,                            // bpm
                0,                              // quantidadeMutacoes
                null,                           // nomeSuperPoder
                null,                           // tipoSuperPoder
                false                           // capturado
        );

        geoResult = new GeocodingResult(
                "Avenida Paulista, 1000",
                "São Paulo",
                "São Paulo",
                "Brasil"
        );
    }

    @Test
    @DisplayName("Deve montar localização com todos os dados corretamente")
    void deveMontarLocalizacaoComTodosDadosCorretamente() {
        // Act
        Localizacao resultado = localizacaoAssembler.montarLocalizacao(patoRequest, cidade, geoResult);

        // Assert
        assertNotNull(resultado);
        assertThat(resultado.getCidade()).isEqualTo(cidade);
        assertThat(resultado.getEndereco()).isEqualTo(geoResult.endereco());
        assertThat(resultado.getPontoReferencia()).isEqualTo(patoRequest.pontoReferencia());
        assertThat(resultado.getCoordenadas()).isNotNull();
        assertThat(resultado.getCoordenadas().getLatitude()).isEqualTo(patoRequest.latitude());
        assertThat(resultado.getCoordenadas().getLongitude()).isEqualTo(patoRequest.longitude());
    }

    @Test
    @DisplayName("Deve criar objeto Coordenadas com latitude e longitude corretas")
    void deveCriarCoordenadasComValoresCorretos() {
        // Act
        Localizacao resultado = localizacaoAssembler.montarLocalizacao(patoRequest, cidade, geoResult);

        // Assert
        Coordenadas coordenadas = resultado.getCoordenadas();
        assertNotNull(coordenadas);
        assertEquals(-23.550520, coordenadas.getLatitude());
        assertEquals(-46.633308, coordenadas.getLongitude());
    }

    @Test
    @DisplayName("Deve associar cidade corretamente à localização")
    void deveAssociarCidadeCorretamente() {
        // Act
        Localizacao resultado = localizacaoAssembler.montarLocalizacao(patoRequest, cidade, geoResult);

        // Assert
        assertSame(cidade, resultado.getCidade());
        assertEquals("São Paulo", resultado.getCidade().getNome());
    }

    @Test
    @DisplayName("Deve usar endereço do GeocodingResult")
    void deveUsarEnderecoDoGeocodingResult() {
        // Act
        Localizacao resultado = localizacaoAssembler.montarLocalizacao(patoRequest, cidade, geoResult);

        // Assert
        assertEquals("Avenida Paulista, 1000", resultado.getEndereco());
    }

    @Test
    @DisplayName("Deve usar ponto de referência do PatoRequest")
    void deveUsarPontoReferenciaDoPatoRequest() {
        // Act
        Localizacao resultado = localizacaoAssembler.montarLocalizacao(patoRequest, cidade, geoResult);

        // Assert
        assertEquals("Próximo ao lago", resultado.getPontoReferencia());
    }

    @Test
    @DisplayName("Deve montar localização com ponto de referência nulo")
    void deveMontarLocalizacaoComPontoReferenciaNulo() {
        // Arrange
        PatoRequest requestSemPontoReferencia = new PatoRequest(
                "DRONE-001",
                "Phantom 4",
                "DJI",
                "China",
                15.5f,
                2.3f,
                -23.550520,
                -46.633308,
                "Brasil",
                "São Paulo",
                "São Paulo",
                null,                           // pontoReferencia nulo
                "Av. Paulista, 1000",
                0.5f,
                EstadoHibernacao.DESPERTO,
                120,
                0,
                null,
                null,
                false
        );

        // Act
        Localizacao resultado = localizacaoAssembler.montarLocalizacao(
                requestSemPontoReferencia, cidade, geoResult
        );

        // Assert
        assertNotNull(resultado);
        assertNull(resultado.getPontoReferencia());
        assertNotNull(resultado.getCoordenadas());
        assertNotNull(resultado.getCidade());
        assertNotNull(resultado.getEndereco());
    }

    @Test
    @DisplayName("Deve montar localização com coordenadas negativas e positivas")
    void deveMontarLocalizacaoComCoordenadasVariadas() {
        // Arrange
        PatoRequest requestCoordenadasPositivas = new PatoRequest(
                "DRONE-001",
                "Phantom 4",
                "DJI",
                "China",
                15.5f,
                2.3f,
                40.7128,                        // latitude positiva
                -74.0060,                       // longitude negativa
                "EUA",
                "Nova York",
                "Nova York",
                "Ponto de referência",
                "Times Square",
                0.5f,
                EstadoHibernacao.DESPERTO,
                120,
                0,
                null,
                null,
                false
        );

        // Act
        Localizacao resultado = localizacaoAssembler.montarLocalizacao(
                requestCoordenadasPositivas, cidade, geoResult
        );

        // Assert
        assertEquals(40.7128, resultado.getCoordenadas().getLatitude());
        assertEquals(-74.0060, resultado.getCoordenadas().getLongitude());
    }

    @Test
    @DisplayName("Deve criar nova instância de Localizacao a cada chamada")
    void deveCriarNovaInstanciaACadaChamada() {
        // Act
        Localizacao resultado1 = localizacaoAssembler.montarLocalizacao(patoRequest, cidade, geoResult);
        Localizacao resultado2 = localizacaoAssembler.montarLocalizacao(patoRequest, cidade, geoResult);

        // Assert
        assertNotSame(resultado1, resultado2);
        assertEquals(resultado1.getEndereco(), resultado2.getEndereco());
        assertEquals(resultado1.getPontoReferencia(), resultado2.getPontoReferencia());
    }
}