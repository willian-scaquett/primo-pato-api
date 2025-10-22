package com.primopato.api.service.assembler;

import com.primopato.api.entity.*;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.service.geocoding.GeocodingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LocalizacaoAssembler {

    public Localizacao montarLocalizacao(PatoRequest patoRequest, Cidade cidade, GeocodingResult geoResult) {

        Localizacao localizacao = new Localizacao();
        localizacao.setCidade(cidade);
        localizacao.setEndereco(geoResult.endereco());
        localizacao.setPontoReferencia(patoRequest.pontoReferencia());
        localizacao.setCoordenadas(new Coordenadas(patoRequest.latitude(), patoRequest.longitude()));

        return localizacao;
    }
}
