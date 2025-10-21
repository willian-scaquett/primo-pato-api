package com.primopato.api.service.assembler;

import com.primopato.api.entity.*;
import com.primopato.api.record.PatoRequest;
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

    public Localizacao montarLocalizacao(Double latitude, Double longitude, String nomePais, String nomeEstado,
                                         String nomeCidade, String pontoReferencia, GeocodingResult geoResult) {

        Pais pais = paisAssembler.obterOuCriarPais(nomePais != null ? nomePais : geoResult.pais());
        Estado estado = estadoAssembler.obterOuCriarEstado(nomeEstado != null ? nomeEstado : geoResult.estado(), pais);
        Cidade cidade = cidadeAssembler.obterOuCriarCidade(nomeCidade != null ? nomeCidade : geoResult.cidade(), estado);

        Localizacao localizacao = new Localizacao();
        localizacao.setCidade(cidade);
        localizacao.setEndereco(geoResult.endereco());
        localizacao.setPontoReferencia(pontoReferencia);
        localizacao.setCoordenadas(new Coordenadas(latitude, longitude));

        return localizacao;
    }
}
