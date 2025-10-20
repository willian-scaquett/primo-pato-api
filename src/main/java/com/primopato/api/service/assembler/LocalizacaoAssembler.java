package com.primopato.api.service.assembler;

import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageResult;
import com.opencagedata.jopencage.model.JOpenCageReverseRequest;
import com.primopato.api.entity.*;
import com.primopato.api.repository.LocalizacaoRepository;
import com.primopato.api.service.geocoding.GeocodingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LocalizacaoAssembler {

    private final CidadeAssembler cidadeAssembler;
    private final EstadoAssembler estadoAssembler;
    private final PaisAssembler paisAssembler;

    public Localizacao montarLocalizacao(Double latitude, Double longitude, GeocodingResult geoResult) {
        Pais pais = paisAssembler.obterOuCriarPais(geoResult.pais());
        Estado estado = estadoAssembler.obterOuCriarEstado(geoResult.estado(), pais);
        Cidade cidade = cidadeAssembler.obterOuCriarCidade(geoResult.cidade(), estado);

        Localizacao localizacao = new Localizacao();
        localizacao.setCidade(cidade);
        localizacao.setEndereco(geoResult.endereco());
        localizacao.setCoordenadas(new Coordenadas(latitude, longitude));

        return localizacao;
    }
}
