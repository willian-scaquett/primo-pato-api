package com.primopato.api.service;

import com.primopato.api.entity.*;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.enumerated.TipoSuperPoder;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.repository.LocalizacaoRepository;
import com.primopato.api.service.assembler.LocalizacaoAssembler;
import com.primopato.api.service.geocoding.GeocodingProvider;
import com.primopato.api.service.geocoding.GeocodingResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocalizacaoServiceTest {

    private LocalizacaoRepository localizacaoRepository;
    private LocalizacaoAssembler localizacaoAssembler;
    private GeocodingProvider geocodingProvider;
    private CidadeService cidadeService;
    private EstadoService estadoService;
    private PaisService paisService;

    private LocalizacaoService localizacaoService;

    @BeforeEach
    void setUp() {
        localizacaoRepository = mock(LocalizacaoRepository.class);
        localizacaoAssembler = mock(LocalizacaoAssembler.class);
        geocodingProvider = mock(GeocodingProvider.class);
        cidadeService = mock(CidadeService.class);
        estadoService = mock(EstadoService.class);
        paisService = mock(PaisService.class);

        localizacaoService = new LocalizacaoService(
                localizacaoRepository,
                localizacaoAssembler,
                geocodingProvider,
                cidadeService,
                estadoService,
                paisService
        );
    }

    private PatoRequest criarPatoRequest(String pais, String estado, String cidade) {
        return new PatoRequest(
                "SN123",
                "ModeloX",
                "FabricanteY",
                "PaisDrone",
                10.5f,
                2.2f,
                10.0,
                20.0,
                pais,
                estado,
                cidade,
                "Ponto Ref",
                "Rua 123",
                1.5f,
                EstadoHibernacao.DESPERTO,
                100,
                0,
                "Correria",
                TipoSuperPoder.ELETRICIDADE,
                false
        );
    }

    @Test
    void testObterOuCriarLocalizacao_Encontrada() {
        PatoRequest patoRequest = criarPatoRequest("PaisZ", "EstadoZ", "CidadeZ");
        Localizacao localizacaoExistente = new Localizacao();

        when(localizacaoRepository.findByCoordenadas(any(Coordenadas.class)))
                .thenReturn(Optional.of(localizacaoExistente));

        Localizacao resultado = localizacaoService.obterOuCriarLocalizacao(patoRequest);

        assertSame(localizacaoExistente, resultado);
        verify(localizacaoRepository).findByCoordenadas(any(Coordenadas.class));
        verifyNoMoreInteractions(localizacaoRepository, geocodingProvider, localizacaoAssembler);
    }

    @Test
    void testObterOuCriarLocalizacao_NaoEncontrada_CriaNova() {
        PatoRequest patoRequest = criarPatoRequest("PaisY", "EstadoY", "CidadeY");

        when(localizacaoRepository.findByCoordenadas(any(Coordenadas.class)))
                .thenReturn(Optional.empty());

        GeocodingResult geocodingResult = new GeocodingResult("Formatted", "GeoCity", "GeoState", "GeoCountry");
        when(geocodingProvider.reverse(10.0, 20.0)).thenReturn(geocodingResult);

        Pais pais = new Pais();
        when(paisService.obterOuCriarPais("PaisY")).thenReturn(pais);

        Estado estado = new Estado();
        when(estadoService.obterOuCriarEstado("EstadoY", pais)).thenReturn(estado);

        Cidade cidade = new Cidade();
        when(cidadeService.obterOuCriarCidade("CidadeY", estado)).thenReturn(cidade);

        Localizacao localizacaoCriada = new Localizacao();
        when(localizacaoAssembler.montarLocalizacao(patoRequest, cidade, geocodingResult))
                .thenReturn(localizacaoCriada);
        when(localizacaoRepository.save(localizacaoCriada)).thenReturn(localizacaoCriada);

        Localizacao resultado = localizacaoService.obterOuCriarLocalizacao(patoRequest);

        assertSame(localizacaoCriada, resultado);
        verify(geocodingProvider).reverse(10.0, 20.0);
        verify(paisService).obterOuCriarPais("PaisY");
        verify(estadoService).obterOuCriarEstado("EstadoY", pais);
        verify(cidadeService).obterOuCriarCidade("CidadeY", estado);
        verify(localizacaoAssembler).montarLocalizacao(patoRequest, cidade, geocodingResult);
        verify(localizacaoRepository).save(localizacaoCriada);
    }

    @Test
    void testCriarLocalizacao_ComFallbacksDoGeocoding() {
        // Request com cidade/estado/pais = null
        PatoRequest patoRequest = criarPatoRequest(null, null, null);

        GeocodingResult geocodingResult = new GeocodingResult("Formatted", "GeoCity", "GeoState", "GeoCountry");
        when(geocodingProvider.reverse(10.0, 20.0)).thenReturn(geocodingResult);

        Pais pais = new Pais();
        Estado estado = new Estado();
        Cidade cidade = new Cidade();
        Localizacao localizacao = new Localizacao();

        when(paisService.obterOuCriarPais("GeoCountry")).thenReturn(pais);
        when(estadoService.obterOuCriarEstado("GeoState", pais)).thenReturn(estado);
        when(cidadeService.obterOuCriarCidade("GeoCity", estado)).thenReturn(cidade);
        when(localizacaoAssembler.montarLocalizacao(patoRequest, cidade, geocodingResult)).thenReturn(localizacao);
        when(localizacaoRepository.save(localizacao)).thenReturn(localizacao);

        Localizacao resultado = invokePrivateCriarLocalizacao(patoRequest);

        assertSame(localizacao, resultado);
        verify(geocodingProvider).reverse(10.0, 20.0);
        verify(paisService).obterOuCriarPais("GeoCountry");
        verify(estadoService).obterOuCriarEstado("GeoState", pais);
        verify(cidadeService).obterOuCriarCidade("GeoCity", estado);
        verify(localizacaoRepository).save(localizacao);
    }

    private Localizacao invokePrivateCriarLocalizacao(PatoRequest patoRequest) {
        try {
            var method = LocalizacaoService.class.getDeclaredMethod("criarLocalizacao", PatoRequest.class);
            method.setAccessible(true);
            return (Localizacao) method.invoke(localizacaoService, patoRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
