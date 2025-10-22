package com.primopato.api.service.assembler;

import com.primopato.api.entity.*;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.utils.LocalizacaoUtils;
import com.primopato.api.utils.UnidadesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PatoAssembler {

    public Pato montarPato(PatoRequest patoRequest, Usuario usuario, Drone drone, SuperPoder superPoder, Pais pais, Localizacao localizacao) {
        Pato pato = new Pato();
        pato.setUsuario(usuario);
        return definirPato(patoRequest, drone, superPoder, pais, localizacao, pato);
    }

    public Pato editarPato(PatoRequest patoRequest, Drone drone, SuperPoder superPoder, Pais pais, Localizacao localizacao, Pato pato) {
        return definirPato(patoRequest, drone, superPoder, pais, localizacao, pato);
    }

    public Pato definirPato(PatoRequest patoRequest, Drone drone, SuperPoder superPoder, Pais pais, Localizacao localizacao, Pato pato) {
        pato.setDroneQueEncontrou(drone);

        //realiza as conversões quando necessário
        boolean isEua = pais.equals(LocalizacaoUtils.EUA);
        pato.setAltura(isEua ? UnidadesUtils.peParaCentimetro(patoRequest.altura()) : patoRequest.altura());
        pato.setPeso(isEua ? UnidadesUtils.libraParaGrama(patoRequest.peso()) : patoRequest.peso());
        pato.setPrecisaoDoGpsQuandoEncontrado(isEua ? UnidadesUtils.jardaParaCentimetro(patoRequest.precisao()) : patoRequest.precisao());

        pato.setLocalizacao(localizacao);

        pato.setQuantidadeMutacoes(patoRequest.quantidadeMutacoes());
        pato.setEstadoHibernacao(patoRequest.estadoHibernacao());

        if (patoRequest.estadoHibernacao().equals(EstadoHibernacao.DESPERTO)) {
            pato.setSuperPoder(superPoder);
            pato.setBpm(null);
        } else {
            pato.setBpm(patoRequest.bpm());
            pato.setSuperPoder(null);
        }

        return pato;
    }
}
