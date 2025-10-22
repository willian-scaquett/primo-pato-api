package com.primopato.api.service;

import com.primopato.api.entity.Coordenadas;
import com.primopato.api.entity.Localizacao;
import com.primopato.api.repository.LocalizacaoRepository;
import com.primopato.api.service.assembler.LocalizacaoAssembler;
import com.primopato.api.service.geocoding.GeocodingProvider;
import com.primopato.api.service.geocoding.GeocodingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LocalizacaoService {

    private final LocalizacaoRepository localizacaoRepository;
    private final LocalizacaoAssembler localizacaoAssembler;
    private final GeocodingProvider geocodingProvider;

    public Localizacao obterOuCriarLocalizacao(Double latitude, Double longitude, String pais, String estado,
                                               String cidade, String pontoReferencia) {

        Optional<Localizacao> optionalLocalizacao = localizacaoRepository.findByCoordenadas(
                new Coordenadas(latitude, longitude)
        );

        if (optionalLocalizacao.isPresent()) {
            return optionalLocalizacao.get();
        }

        GeocodingResult geoResult = geocodingProvider.reverse(latitude, longitude);
        Localizacao localizacao = localizacaoAssembler.montarLocalizacao(latitude, longitude, pais, estado, cidade,
                pontoReferencia, geoResult);

        return localizacaoRepository.save(localizacao);
    }
}
