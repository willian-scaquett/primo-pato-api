package com.primopato.api.service;

import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageResponse;
import com.opencagedata.jopencage.model.JOpenCageResult;
import com.opencagedata.jopencage.model.JOpenCageReverseRequest;
import com.primopato.api.entity.*;
import com.primopato.api.repository.LocalizacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LocalizacaoService {

    private static final String OPEN_CAGE_API_KEY = "bda0a070c2b34c8f894ab21fb5f39022";
    private static final String LANGUAGE = "pt-BR";

    private final LocalizacaoRepository localizacaoRepository;
    private final CidadeService cidadeService;
    private final EstadoService estadoService;
    private final PaisService paisService;

    public Localizacao getLocalizaoPorCoordenada(Double latitude, Double longitude) {
        Optional<Localizacao> optionalLocalizacao = localizacaoRepository.findByCoordenadas(new Coordenadas(latitude, longitude));

        if (optionalLocalizacao.isPresent()) {
            return optionalLocalizacao.get();
        }

        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(OPEN_CAGE_API_KEY);

        JOpenCageReverseRequest request = new JOpenCageReverseRequest(latitude, longitude);
        request.setLanguage(LANGUAGE);
        request.setLimit(1);

        JOpenCageResult response = jOpenCageGeocoder.reverse(request).getResults().getFirst();

        Localizacao localizacao = new Localizacao();

        //Localiza (se não existir, cria) o registro do país no banco
        Pais pais = paisService.getPais(response.getComponents().getCountry());

        //Localiza (se não existir, cria) o registro do estado para aquele país
        Estado estado =
                pais.getEstados().stream()
                .filter(e -> e.getNome().equals(response.getComponents().getState().toUpperCase()))
                .findFirst()
                .orElse(estadoService.cadastrarEstado(response.getComponents().getState(), pais));

        //Localiza (se não existir, cria) o registro da cidade para aquele estado
        Cidade cidade =
                estado.getCidades().stream()
                        .filter(c -> c.getNome().equals(response.getComponents().getCity().toUpperCase()))
                        .findFirst()
                        .orElse(cidadeService.cadastrarCidade(response.getComponents().getCity(), estado));

        localizacao.setCidade(cidade);
        localizacao.setEndereco(response.getFormatted());
        localizacao.setCoordenadas(new Coordenadas(latitude, longitude));
        //localizacao.setPontoReferencia(); pensar sobre

        return localizacaoRepository.save(localizacao);
    }
}
