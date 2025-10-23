package com.primopato.api.service;

import com.primopato.api.entity.Coordenadas;
import com.primopato.api.entity.Estado;
import com.primopato.api.entity.Localizacao;
import com.primopato.api.entity.Pais;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.repository.LocalizacaoRepository;
import com.primopato.api.service.assembler.LocalizacaoAssembler;
import com.primopato.api.service.geocoding.GeocodingProvider;
import com.primopato.api.service.geocoding.GeocodingResult;
import com.primopato.api.utils.CustomStringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LocalizacaoService {

    private final LocalizacaoRepository localizacaoRepository;
    private final LocalizacaoAssembler localizacaoAssembler;
    private final GeocodingProvider geocodingProvider;
    private final CidadeService cidadeService;
    private final EstadoService estadoService;
    private final PaisService paisService;

    public Localizacao obterOuCriarLocalizacao(PatoRequest patoRequest) {
        log.info("Buscando localização com as coordenadas lat:{} lon:{}", patoRequest.latitude(), patoRequest.longitude());
        return localizacaoRepository
                .findByCoordenadas(new Coordenadas(patoRequest.latitude(), patoRequest.longitude()))
                .orElseGet(() -> {
                    log.info("Localização não encontrada. Criando nova: {} - {}", patoRequest.latitude(), patoRequest.longitude());
                    return criarLocalizacao(patoRequest);
                });
    }

    private Localizacao criarLocalizacao(PatoRequest patoRequest) {
        GeocodingResult geocodingResult = geocodingProvider.reverse(patoRequest.latitude(), patoRequest.longitude());

        Pais pais = paisService.obterOuCriarPais(CustomStringUtils.coalesce(patoRequest.pais(), geocodingResult.pais()));
        Estado estado = estadoService.obterOuCriarEstado(CustomStringUtils.coalesce(patoRequest.pais(), geocodingResult.estado()), pais);

        return localizacaoRepository.save(
                localizacaoAssembler.montarLocalizacao(
                        patoRequest,
                        cidadeService.obterOuCriarCidade(CustomStringUtils.coalesce(patoRequest.cidade(), geocodingResult.cidade()), estado),
                        geocodingResult
                )
        );
    }
}
