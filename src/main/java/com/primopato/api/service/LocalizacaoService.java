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
import com.primopato.api.utils.StringUtils;
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
        return localizacaoRepository
                .findByCoordenadas(new Coordenadas(patoRequest.latitude(), patoRequest.longitude()))
                .orElseGet(() -> {
                    log.info("Localização não encontrada. Criando nova: {} - {}", patoRequest.latitude(), patoRequest.longitude());
                    return localizacaoRepository.save(criarLocalizacao(patoRequest));
                });
    }

    private Localizacao criarLocalizacao(PatoRequest patoRequest) {
        GeocodingResult geocodingResult = geocodingProvider.reverse(patoRequest.latitude(), patoRequest.longitude());

        Pais pais = paisService.obterOuCriarPais(StringUtils.coalesce(patoRequest.pais(), geocodingResult.pais()));
        Estado estado = estadoService.obterOuCriarEstado(StringUtils.coalesce(patoRequest.pais(), geocodingResult.estado()), pais);

        return localizacaoRepository.save(
                localizacaoAssembler.montarLocalizacao(
                        patoRequest,
                        cidadeService.obterOuCriarCidade(StringUtils.coalesce(patoRequest.cidade(), geocodingResult.cidade()), estado),
                        geocodingResult
                )
        );
    }
}
